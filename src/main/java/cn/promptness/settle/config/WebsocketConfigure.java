package cn.promptness.settle.config;


import cn.promptness.settle.utils.SpringContextHolderUtil;

import javax.websocket.server.ServerEndpointConfig;

public class WebsocketConfigure extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return SpringContextHolderUtil.getBean(clazz);
    }

}
