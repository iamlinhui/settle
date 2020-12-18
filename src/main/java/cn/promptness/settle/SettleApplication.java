package cn.promptness.settle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lynn
 * @date 2020/5/22 11:19
 * @since v1.0.0
 */
@SpringBootApplication
public class SettleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SettleApplication.class, args);
    }

    /**
     * 用途：扫描并注册所有携带@ServerEndpoint注解的实例。 @ServerEndpoint("/websocket")
     * PS：如果使用外部容器 则无需提供ServerEndpointExporter。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
