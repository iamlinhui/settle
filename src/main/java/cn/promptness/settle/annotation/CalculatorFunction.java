package cn.promptness.settle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lynn
 * @date 2020/5/25 15:17
 * @since v1.0.0
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface CalculatorFunction {

    /**
     * 注意:需要public 和 static 的方法
     * <p>
     * 自定义函数片的方法名 functionName
     *
     * @author lynn
     * @date 2020/5/25 15:58
     * @since v1.0.0
     */
    String value() default "";

}
