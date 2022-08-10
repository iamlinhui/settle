package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MiddleInterestRule extends AbstractInterestRule {

    @ContextRule(value = "MRD", desc = "中间期还款日", clazz = Date.class)
    private String middleRepayDateRule;

    @ContextRule(value = "MIA", desc = "中间期利息", clazz = BigDecimal.class)
    private String middleInterestAmountRule;

    @ContextRule(value = "MPA", desc = "中间期本金", clazz = BigDecimal.class)
    private String middlePrincipalAmountRule;

    @ContextRule(value = "MTA", desc = "中间期总额", clazz = BigDecimal.class)
    private String middleTotalAmountRule;

    @ContextRule(value = "NPA", desc = "未还本金", clazz = BigDecimal.class)
    private String notPrincipalAmountRule = "NPA.sub(MPA).max(0).off(RP)";

    @ContextRule(value = "MDC", desc = "中间期天数", clazz = Integer.class)
    private String betweenDaysRule = "DAYS_FUNCTION(MONTH_ADD_FUNCTION(MRD,-1),MRD)";

    @ContextRule(value = "CRD", desc = "当前期还款日", clazz = Date.class)
    private String currentRepayDateRule = "MRD";

    @ContextRule(value = "CIA", desc = "当前期利息", clazz = BigDecimal.class)
    private String currentInterestAmountRule = "MIA";

    @ContextRule(value = "CPA", desc = "当前期本金", clazz = BigDecimal.class)
    private String currentPrincipalAmountRule = "MPA";

    @ContextRule(value = "CTA", desc = "当前期总额", clazz = BigDecimal.class)
    private String currentTotalAmountRule = "MTA";

    @ContextRule(value = "CDC", desc = "当前期天数", clazz = Integer.class)
    private String currentBetweenDaysRule = "MDC";

    @ContextRule(value = "PRD", desc = "前一期还款日", clazz = Date.class)
    private String preRepayDateRule = "MRD";
}
