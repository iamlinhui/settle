package cn.promptness.settle.calculator.function;

import cn.promptness.settle.annotation.CalculatorFunction;
import cn.promptness.settle.calculator.element.SettleDecimal;
import org.springframework.stereotype.Component;

/**
 * @author lynn
 * @date 2020/5/26 10:29
 * @since v1.0.0
 */
@Component
public class NumberFunction {

    @CalculatorFunction("NUM")
    public static SettleDecimal number(Number number) {
        return new SettleDecimal(number.toString());
    }
}
