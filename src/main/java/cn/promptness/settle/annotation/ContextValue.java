package cn.promptness.settle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 按照标示 从上下文中取值，并填充到当前对象中
 *
 * @author lynn
 * @date 2020/6/23 19:18
 * @since v1.0.0
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ContextValue {

    /**
     * 取值 的key
     */
    String value();
}
