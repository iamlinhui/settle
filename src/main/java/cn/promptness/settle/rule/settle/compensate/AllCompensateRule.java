package cn.promptness.settle.rule.settle.compensate;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import cn.promptness.settle.rule.settle.payoff.BasePayoffRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AllCompensateRule extends AbstractRule {

    @ContextRule(desc = "赔付结清-单期代偿", order = 1)
    private SingleCompensateRule singleCompensateRule;

    @ContextRule(desc = "赔付结清-提前结清", order = 2)
    private BasePayoffRule basePayoffRule;

}
