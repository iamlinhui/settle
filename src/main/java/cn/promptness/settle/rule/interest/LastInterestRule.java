package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.calculator.element.SettleDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class LastInterestRule extends AbstractInterestRule {

    @ContextRule(value = "LRD", desc = "尾期还款日", clazz = Date.class)
    private String lastRepayDateRule;

    @ContextRule(value = "LIA", desc = "尾期利息", clazz = SettleDecimal.class)
    private String lastInterestAmountRule;

    @ContextRule(value = "LPA", desc = "尾期本金", clazz = SettleDecimal.class)
    private String lastPrincipalAmountRule;

    @ContextRule(value = "LTA", desc = "尾期总额", clazz = SettleDecimal.class)
    private String lastTotalAmountRule;

    @ContextRule(value = "NPA", desc = "未还本金", clazz = SettleDecimal.class)
    private String notPrincipalAmountRule = "#NPA.subtract(#LPA)";


    @ContextRule(value = "CRD", desc = "当前期还款日", clazz = Date.class)
    private String currentRepayDateRule = "#LRD";

    @ContextRule(value = "CIA", desc = "当前期利息", clazz = SettleDecimal.class)
    private String currentInterestAmountRule = "#LIA";

    @ContextRule(value = "CPA", desc = "当前期本金", clazz = SettleDecimal.class)
    private String currentPrincipalAmountRule = "#LPA";

    @ContextRule(value = "CTA", desc = "当前期总额", clazz = SettleDecimal.class)
    private String currentTotalAmountRule = "#LTA";
}
