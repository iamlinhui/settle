package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.calculator.element.SettleDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstInterestRule extends AbstractInterestRule {


    @ContextRule(value = "FSD", desc = "首期起息日", clazz = Date.class, order = 1)
    private String firstStartDateRule;

    @ContextRule(value = "FRD", desc = "首期还款日", clazz = Date.class, order = 1)
    private String firstRepayDateRule;

    @ContextRule(value = "FIA", desc = "首期利息", clazz = SettleDecimal.class)
    private String firstInterestAmountRule;

    @ContextRule(value = "FPA", desc = "首期本金", clazz = SettleDecimal.class)
    private String firstPrincipalAmountRule;

    @ContextRule(value = "FTA", desc = "首期总额", clazz = SettleDecimal.class)
    private String firstTotalAmountRule;

    @ContextRule(value = "NPA", desc = "未还本金", clazz = SettleDecimal.class)
    private String notPrincipalAmountRule = "#LA.subtract(#FPA).max(0,#RP)";

    @ContextRule(value = "FDC", desc = "首期天数", clazz = SettleDecimal.class)
    private String betweenDaysRule = "#DAYS_FUNCTION(#FSD,#FRD)";

    @ContextRule(value = "CRD", desc = "当前期还款日", clazz = Date.class)
    private String currentRepayDateRule = "#FRD";

    @ContextRule(value = "CIA", desc = "当前期利息", clazz = SettleDecimal.class)
    private String currentInterestAmountRule = "#FIA";

    @ContextRule(value = "CPA", desc = "当前期本金", clazz = SettleDecimal.class)
    private String currentPrincipalAmountRule = "#FPA";

    @ContextRule(value = "CTA", desc = "当前期总额", clazz = SettleDecimal.class)
    private String currentTotalAmountRule = "#FTA";

    @ContextRule(value = "CDC", desc = "当前期天数", clazz = SettleDecimal.class)
    private String currentBetweenDaysRule = "#FDC";

    @ContextRule(value = "PRD", desc = "前一期还款日", clazz = Date.class)
    private String preRepayDateRule = "#FRD";
}
