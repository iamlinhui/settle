package cn.promptness.settle.rule.base;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author lynn
 * @date 2020/6/11 14:14
 * @since v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RateInfoRule extends AbstractRule {

    @ContextRule(value = "YR", desc = "年利率", clazz = BigDecimal.class)
    private String yearRateRule;
    @ContextRule(value = "MR", desc = "月利率", clazz = BigDecimal.class)
    private String monthOfYearRateRule;
    @ContextRule(value = "DR", desc = "日利率", clazz = BigDecimal.class)
    private String dayOfYearRateRule;


    @ContextRule(value = "MYR", desc = "罚息年利率", clazz = BigDecimal.class)
    private String mulctRateRule;
    @ContextRule(value = "MMR", desc = "罚息月利率", clazz = BigDecimal.class)
    private String monthOfMulctRateRule;
    @ContextRule(value = "DMR", desc = "罚息日利率", clazz = BigDecimal.class)
    private String dayOfMulctRateRule;


    @ContextRule(value = "PYR", desc = "结清利率", clazz = BigDecimal.class)
    private String payoffRateRule;
    @ContextRule(value = "MPR", desc = "结清月利率", clazz = BigDecimal.class)
    private String monthOfPayoffRateRule;
    @ContextRule(value = "DPR", desc = "结清日利率", clazz = BigDecimal.class)
    private String dayOfPayoffRateRule;


    @ContextRule(value = "OYR", desc = "资金占用利率", clazz = BigDecimal.class)
    private String occupyRateRule;
    @ContextRule(value = "MOR", desc = "资金占用月利率", clazz = BigDecimal.class)
    private String monthOfOccupyRateRule;
    @ContextRule(value = "DOR", desc = "资金占用日利率", clazz = BigDecimal.class)
    private String dayOfOccupyRateRule;


    @ContextRule(value = "CYR", desc = "赔付利率", clazz = BigDecimal.class)
    private String compensateRateRule;
    @ContextRule(value = "MCR", desc = "赔付月利率", clazz = BigDecimal.class)
    private String monthOfCompensateRateRule;
    @ContextRule(value = "DCR", desc = "赔付日利率", clazz = BigDecimal.class)
    private String dayOfCompensateRateRule;
}
