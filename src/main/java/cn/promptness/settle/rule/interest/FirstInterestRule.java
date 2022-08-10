package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstInterestRule extends AbstractInterestRule {


    @ContextRule(value = "FSD", desc = "首期起息日", clazz = Date.class, order = 1)
    private String firstStartDateRule;

    @ContextRule(value = "FRD", desc = "首期还款日", clazz = Date.class, order = 1)
    private String firstRepayDateRule;

    @ContextRule(value = "FIA", desc = "首期利息", clazz = BigDecimal.class)
    private String firstInterestAmountRule;

    @ContextRule(value = "FPA", desc = "首期本金", clazz = BigDecimal.class)
    private String firstPrincipalAmountRule;

    @ContextRule(value = "FTA", desc = "首期总额", clazz = BigDecimal.class)
    private String firstTotalAmountRule;

    @ContextRule(value = "NPA", desc = "未还本金", clazz = BigDecimal.class)
    private String notPrincipalAmountRule = "LA.sub(FPA).max(0).off(RP)";

    @ContextRule(value = "FDC", desc = "首期天数", clazz = Integer.class)
    private String betweenDaysRule = "FRD - FSD";

    @ContextRule(value = "CRD", desc = "当前期还款日", clazz = Date.class)
    private String currentRepayDateRule = "FRD";

    @ContextRule(value = "CIA", desc = "当前期利息", clazz = BigDecimal.class)
    private String currentInterestAmountRule = "FIA";

    @ContextRule(value = "CPA", desc = "当前期本金", clazz = BigDecimal.class)
    private String currentPrincipalAmountRule = "FPA";

    @ContextRule(value = "CTA", desc = "当前期总额", clazz = BigDecimal.class)
    private String currentTotalAmountRule = "FTA";

    @ContextRule(value = "CDC", desc = "当前期天数", clazz = Integer.class)
    private String currentBetweenDaysRule = "FDC";

    @ContextRule(value = "PRD", desc = "前一期还款日", clazz = Date.class)
    private String preRepayDateRule = "FRD";
}
