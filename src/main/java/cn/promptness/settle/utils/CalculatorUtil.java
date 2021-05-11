package cn.promptness.settle.utils;

import cn.promptness.settle.annotation.ContextField;
import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.annotation.ContextValue;
import cn.promptness.settle.exception.ContextException;
import cn.promptness.settle.rule.AbstractRule;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 计算工具
 *
 * @author lynn
 * @date 2020/7/6 11:03
 * @since v1.0.0
 */
public class CalculatorUtil {

    private static final Logger log = LoggerFactory.getLogger(CalculatorUtil.class);

    public static void parseFieldToContext(final StandardEvaluationContext context, final Object param) {
        ReflectionUtils.doWithFields(param.getClass(), field -> {
            ContextField annotation = choose(field, ContextField.class);
            if (annotation != null) {
                ReflectionUtils.makeAccessible(field);
                Object fieldObj = ReflectionUtils.getField(field, param);
                context.setVariable(annotation.value(), fieldObj);
            }
        });
    }

    public static void parseContextToValue(final StandardEvaluationContext context, final Object param) {
        ReflectionUtils.doWithFields(param.getClass(), field -> {
            ContextValue annotation = choose(field, ContextValue.class);
            if (annotation != null) {
                ReflectionUtils.makeAccessible(field);
                String value = annotation.value();
                ReflectionUtils.setField(field, param, context.lookupVariable(value));
            }
        });
    }

    public static void parseRuleToContext(final StandardEvaluationContext context, final AbstractRule rule) {

        final List<Field> fieldList = Lists.newArrayList();
        ReflectionUtils.doWithFields(rule.getClass(), field -> {
            if (choose(field, ContextRule.class) != null) {
                fieldList.add(field);
            }
        });
        List<Field> ruleFileList = Lists.newArrayList(fieldList).stream().sorted(CalculatorUtil::orderContextRule).collect(Collectors.toList());
        if (ruleFileList.isEmpty()) {
            return;
        }

        for (Field field : ruleFileList) {
            ReflectionUtils.makeAccessible(field);
            if (Objects.equals(String.class, field.getType())) {
                ContextRule annotation = field.getAnnotation(ContextRule.class);
                Object ruleObj = ReflectionUtils.getField(field, rule);
                if (ruleObj != null) {
                    Object parse = CalculatorUtil.parseRule((String) ruleObj, annotation.clazz(), context);
                    context.setVariable(annotation.value(), parse);
                    log.debug("[{}] {}={} ==> {}", annotation.desc(), annotation.value(), ruleObj, parse);
                }
                continue;
            }
            if (AbstractRule.class.isAssignableFrom(field.getType())) {
                // 继续解析
                Object subRule = ReflectionUtils.getField(field, rule);
                if (subRule != null) {
                    parseRuleToContext(context, (AbstractRule) subRule);
                }
            }
        }
    }

    public static <T> T parseRule(String rule, Class<T> clazz, StandardEvaluationContext context, ParserContext parserContext) {
        Expression expression = EXPRESSION_PARSER.parseExpression(rule, parserContext);
        return expression.getValue(context, clazz);
    }

    public static <T> T parseRule(String rule, Class<T> clazz, StandardEvaluationContext context) {
        Expression expression = EXPRESSION_PARSER.parseExpression(rule);
        return expression.getValue(context, clazz);
    }

    private static int orderContextRule(Field one, Field two) {
        ContextRule oneAnnotation = AnnotationUtils.getAnnotation(one, ContextRule.class);
        ContextRule twoAnnotation = AnnotationUtils.getAnnotation(two, ContextRule.class);
        if (oneAnnotation == null || twoAnnotation == null) {
            throw new ContextException("@Context 为空！请核查代码，先做skip过滤！");
        }
        return oneAnnotation.order() - twoAnnotation.order();
    }

    private static <A extends Annotation> A choose(Field field, Class<A> annotationType) {
        A annotation = AnnotationUtils.getAnnotation(field, annotationType);
        if (annotation == null || Modifier.isStatic(field.getModifiers())) {
            return null;
        }
        return annotation;
    }

    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private CalculatorUtil() {
    }

}
