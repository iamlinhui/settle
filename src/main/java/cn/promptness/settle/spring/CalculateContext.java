package cn.promptness.settle.spring;

import cn.promptness.settle.annotation.CalculatorFunction;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lynn
 * @date 2020/5/25 12:53
 * @since v1.0.0
 */
@Component
public class CalculateContext implements BeanFactoryPostProcessor, BeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    private final Map<String, Method> functionMap = Maps.newConcurrentMap();

    public StandardEvaluationContext newContext() {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.addPropertyAccessor(new BeanExpressionContextAccessor());
        standardEvaluationContext.addPropertyAccessor(new BeanFactoryAccessor());
        standardEvaluationContext.addPropertyAccessor(new MapAccessor());
        standardEvaluationContext.addPropertyAccessor(new EnvironmentAccessor());
        standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        standardEvaluationContext.setTypeLocator(new StandardTypeLocator(beanFactory.getBeanClassLoader()));
        ConversionService conversionService = beanFactory.getConversionService();
        if (conversionService != null) {
            standardEvaluationContext.setTypeConverter(new StandardTypeConverter(conversionService));
        }

        for (Map.Entry<String, Method> methodEntry : functionMap.entrySet()) {
            standardEvaluationContext.registerFunction(methodEntry.getKey(), methodEntry.getValue());
        }

        return standardEvaluationContext;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

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
                functionMap.put(functionName, method);
            } else {
                functionMap.put(calculatorFunction.value(), method);
            }
        }

        return bean;
    }


}
