package cn.promptness.settle.rule.settle.payoff;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.enums.RepayType;
import cn.promptness.settle.calculator.element.SettleDecimal;
import cn.promptness.settle.rule.settle.AbstractSettleRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PayoffRule extends AbstractSettleRule {

    @ContextRule(value = "NRPA", desc = "下一期实还本金", clazz = SettleDecimal.class)
    private String nextRealRepayPrincipalAmountRule = "#NEPA";

    @ContextRule(value = "NRIA", desc = "下一期实还利息", clazz = SettleDecimal.class)
    private String nextRealRepayInterestAmountRule;

    @ContextRule(value = "NRRT", desc = "下一期实还类型", clazz = RepayType.class)
    private String nextRealRepayTypeRule;

    @ContextRule(value = "NRRD", desc = "下一期实还结算日期", clazz = Date.class)
    private String nextRealRepayDateRule;

    @ContextRule(value = "NRSD", desc = "下一期实还截息日期", clazz = Date.class)
    private String nextRealSettleDateRule;

}
