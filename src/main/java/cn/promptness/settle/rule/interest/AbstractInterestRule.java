package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.calculator.element.SettleDecimal;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractInterestRule extends AbstractRule {

    @ContextRule(value = "MA", desc = "每期公式月供", clazz = SettleDecimal.class, order = 0)
    private String monthAmountRule;

}
