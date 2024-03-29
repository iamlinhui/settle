package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class LastInterestRule extends AbstractInterestRule {

    @ContextRule(value = "LRD", desc = "尾期还款日", clazz = Date.class)
    private String lastRepayDateRule;

    @ContextRule(value = "LIA", desc = "尾期利息", clazz = BigDecimal.class)
    private String lastInterestAmountRule;

    @ContextRule(value = "LPA", desc = "尾期本金", clazz = BigDecimal.class)
    private String lastPrincipalAmountRule;

    @ContextRule(value = "LTA", desc = "尾期总额", clazz = BigDecimal.class)
    private String lastTotalAmountRule;

    @ContextRule(value = "NPA", desc = "未还本金", clazz = BigDecimal.class)
    private String notPrincipalAmountRule = "NPA.sub(LPA).max(0).off(RP)";

    @ContextRule(value = "LDC", desc = "尾期天数", clazz = Integer.class)
    private String betweenDaysRule = "DAYS_FUNCTION(MONTH_ADD_FUNCTION(LRD,-1),LRD)";

    @ContextRule(value = "CRD", desc = "当前期还款日", clazz = Date.class)
    private String currentRepayDateRule = "LRD";

    @ContextRule(value = "CIA", desc = "当前期利息", clazz = BigDecimal.class)
    private String currentInterestAmountRule = "LIA";

    @ContextRule(value = "CPA", desc = "当前期本金", clazz = BigDecimal.class)
    private String currentPrincipalAmountRule = "LPA";

    @ContextRule(value = "CTA", desc = "当前期总额", clazz = BigDecimal.class)
    private String currentTotalAmountRule = "LTA";

    @ContextRule(value = "CDC", desc = "当前期天数", clazz = Integer.class)
    private String currentBetweenDaysRule = "LDC";
}
