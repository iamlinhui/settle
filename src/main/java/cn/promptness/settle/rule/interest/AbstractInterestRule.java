package cn.promptness.settle.rule.interest;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractInterestRule extends AbstractRule {

    @ContextRule(value = "MA", desc = "每期公式月供", clazz = BigDecimal.class, order = 0)
    private String monthAmountRule;

}
