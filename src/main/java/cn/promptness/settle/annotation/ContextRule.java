package cn.promptness.settle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lynn
 * @date 2020/6/11 14:11
 * @since v1.0.0
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ContextRule {

    /**
     * 如果需要注册到计算上下文，则需要填充此属性值
     * 计算上下文的key eg:YR
     */
    String value() default "";

    String desc() default "";

    /**
     * 计算先后顺序
     */
    int order() default Integer.MAX_VALUE;

    /**
     * 解析后的结果类型
     */
    Class<?> clazz() default Object.class;
}
