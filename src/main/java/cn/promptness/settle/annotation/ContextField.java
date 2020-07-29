package cn.promptness.settle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 将对象中的属性值 填充到上下文中的标示
 *
 * @author lynn
 * @date 2020/6/23 19:18
 * @since v1.0.0
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ContextField {

    /**
     * 如果需要注册到计算上下文，则需要填充此属性值
     * <p>
     * 计算上下文的key eg:YR
     */
    String value();
}
