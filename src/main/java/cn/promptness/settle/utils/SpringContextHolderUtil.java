package cn.promptness.settle.utils;

import cn.promptness.settle.exception.ContextException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author lynn
 * @date 2020/7/9 12:22
 * @since v1.0.0
 */
@Component
public class SpringContextHolderUtil implements ApplicationContextAware, DisposableBean {

    private static volatile ApplicationContext shareApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (shareApplicationContext == null) {
            synchronized (SpringContextHolderUtil.class) {
                if (shareApplicationContext == null) {
                    shareApplicationContext = applicationContext;
                }
            }
        }
    }

    @Override
    public void destroy() {
        if (shareApplicationContext != null) {
            synchronized (SpringContextHolderUtil.class) {
                if (shareApplicationContext != null) {
                    shareApplicationContext = null;
                }
            }
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    private static ApplicationContext getApplicationContext() {
        if (shareApplicationContext == null) {
            throw new ContextException("applicationContext属性为null,请检查是否注入了SpringContextHolderUtil!");
        }
        return shareApplicationContext;
    }
}