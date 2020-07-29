package cn.promptness.settle.rule.settle.compensate;

import cn.promptness.settle.rule.AbstractRule;
import cn.promptness.settle.rule.settle.overdue.OverdueRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SingleCompensateRule extends AbstractRule {

    private OverdueRule overdueRule;

}
