package cn.promptness.settle.spring;

import cn.holmes.settle.expression.lang.opt.custom.CustomMake;
import cn.promptness.settle.annotation.CalculatorFunction;
import com.google.common.base.CaseFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lynn
 * @date 2020/5/25 12:53
 * @since v1.0.0
 */
@Component
public class CalculateContext implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, method -> {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                res.add(method);
            }
        });

        for (Method method : res) {
            CalculatorFunction calculatorFunction = method.getAnnotation(CalculatorFunction.class);
            if (calculatorFunction == null) {
                continue;
            }
            if (StringUtils.isEmpty(calculatorFunction.value())) {
                String functionName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, method.getName());
                CustomMake.me().register(functionName, method);
            } else {
                CustomMake.me().register(calculatorFunction.value(), method);
            }
        }

        return bean;
    }


}
