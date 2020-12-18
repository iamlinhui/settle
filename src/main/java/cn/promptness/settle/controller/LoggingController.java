package cn.promptness.settle.controller;

import cn.promptness.settle.config.WebsocketConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author lynn
 * @date 2020/7/9 16:09
 * @since v1.0.0
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/logging", configurator = WebsocketConfigure.class)
public class LoggingController {

    @Value("${logging.path}")
    private String loggingPath;

    @Value("${logging.file}")
    private String loggingFile;

    /**
     * 任务分配线程池
     */
    private static final ExecutorService TASK_THREAD_POOL = new ThreadPoolExecutor(
            3, 10, 5L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 连接集合
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Integer> LENGTH_MAP = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        //添加到集合中
        SESSION_MAP.put(session.getId(), session);
        //默认从第一行开始
        LENGTH_MAP.put(session.getId(), 1);

        //获取日志信息
        TASK_THREAD_POOL.submit(() -> {
            log.info("LoggingWebSocketServer 任务开始");
            boolean first = true;
            while (SESSION_MAP.get(session.getId()) != null && LENGTH_MAP.get(session.getId()) != null) {
                BufferedReader reader = null;
                try {
                    //日志文件路径，获取最新的
                    String filePath = loggingPath + loggingFile;

                    //字符流
                    reader = new BufferedReader(new FileReader(filePath));
                    Object[] lines = reader.lines().toArray();

                    //只取从上次之后产生的日志
                    Object[] copyOfRange = Arrays.copyOfRange(lines, LENGTH_MAP.get(session.getId()), lines.length);

                    //存储最新一行开始
                    LENGTH_MAP.put(session.getId(), lines.length);

                    //第一次如果太大，截取最新的200行就够了，避免传输的数据太大
                    if (first && copyOfRange.length > 200) {
                        copyOfRange = Arrays.copyOfRange(copyOfRange, copyOfRange.length - 200, copyOfRange.length);
                        first = false;
                    }

                    //发送
                    send(session, copyOfRange);

                    //休眠一秒
                    Thread.sleep(1000);
                } catch (Exception e) {
                    //捕获但不处理
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            log.info("LoggingWebSocketServer 任务结束");
        });
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //从集合中删除
        SESSION_MAP.remove(session.getId());
        LENGTH_MAP.remove(session.getId());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {

    }

    /**
     * 封装一个send方法，发送消息到前端
     */
    private void send(Session session, Object[] message) {
        try {
            if (message.length == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : message) {
                if (o.toString().contains("CalculatorUtil") && o.toString().contains("|")) {
                    stringBuilder.append(o.toString().split("\\|")[1]).append("<br/>");
                }
            }
            session.getBasicRemote().sendText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}