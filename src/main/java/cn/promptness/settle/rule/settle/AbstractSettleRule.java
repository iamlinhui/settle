package cn.promptness.settle.rule.settle;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.enums.RepayType;
import cn.promptness.settle.rule.AbstractRule;
import cn.promptness.settle.calculator.element.SettleDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 实还规则
 *
 * @author lynn
 * @date 2020/6/11 16:27
 * @since v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractSettleRule extends AbstractRule {

    @ContextRule(value = "RPA", desc = "实还本金", clazz = SettleDecimal.class, order = 1)
    private String realRepayPrincipalAmountRule = "#EPA";

    @ContextRule(value = "RIA", desc = "实还利息", clazz = SettleDecimal.class, order = 1)
    private String realRepayInterestAmountRule;

    @ContextRule(value = "RRT", desc = "实还类型", clazz = RepayType.class, order = 1)
    private String realRepayTypeRule;

    @ContextRule(value = "RRD", desc = "实还结算日期", clazz = Date.class, order = 1)
    private String realRepayDateRule;

    @ContextRule(value = "RSD", desc = "实还截息日期", clazz = Date.class, order = 1)
    private String realSettleDateRule;

}
