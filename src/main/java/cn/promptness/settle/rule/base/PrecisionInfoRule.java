package cn.promptness.settle.rule.base;

import cn.holmes.settle.expression.common.element.Precision;
import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lynn
 * @date 2020/6/11 14:33
 * @since v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PrecisionInfoRule extends AbstractRule {

    @ContextRule(value = "RP", desc = "结果精度", clazz = Precision.class)
    private String resultPrecisionRule;

    @ContextRule(value = "PP", desc = "过程精度", clazz = Precision.class)
    private String processPrecisionRule;

    @ContextRule(value = "MRP", desc = "月利率精度", clazz = Precision.class)
    private String monthRatePrecisionRule;

    @ContextRule(value = "DRP", desc = "日利率精度", clazz = Precision.class)
    private String dayRatePrecisionRule;

    @ContextRule(value = "PRP", desc = "预处理精度", clazz = Precision.class)
    private String prePrecisionRule;

    @ContextRule(value = "PWP", desc = "次方精度", clazz = Precision.class)
    private String powPrecisionRule;


}
