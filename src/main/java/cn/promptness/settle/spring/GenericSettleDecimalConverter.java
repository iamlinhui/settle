package cn.promptness.settle.spring;

import cn.promptness.settle.calculator.element.SettleDecimal;
import com.google.common.collect.ImmutableSet;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;

/**
 *
 * 类型转换器
 *
 * @author lynn
 * @date 2020/5/26 18:13
 * @since v1.0.0
 */
public class GenericSettleDecimalConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair[] pairs = new ConvertiblePair[]{
                new ConvertiblePair(Number.class, SettleDecimal.class),
                new ConvertiblePair(String.class, SettleDecimal.class)};
        return ImmutableSet.copyOf(pairs);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == SettleDecimal.class) {
            return source;
        }
        if (sourceType.getType() == String.class) {
            String number = (String) source;
            return new SettleDecimal(number);
        } else {
            Number number = (Number) source;
            return new SettleDecimal(number.toString());
        }
    }
}
