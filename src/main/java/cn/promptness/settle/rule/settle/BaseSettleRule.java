package cn.promptness.settle.rule.settle;

import cn.promptness.settle.rule.AbstractRule;
import cn.promptness.settle.rule.settle.payoff.BasePayoffRule;
import cn.promptness.settle.rule.settle.compensate.AllCompensateRule;
import cn.promptness.settle.rule.settle.compensate.SingleCompensateRule;
import cn.promptness.settle.rule.settle.normal.NormalRule;
import cn.promptness.settle.rule.settle.overdue.OverdueRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseSettleRule extends AbstractRule {

    /**
     * 正常还款
     */
    private NormalRule normalRule;

    /**
     * 提前结清
     */
    private BasePayoffRule basePayoffRule;

    /**
     * 逾期还款
     */
    private OverdueRule overdueRule;

    /**
     * 单期代偿
     */
    private SingleCompensateRule singleCompensateRule;

    /**
     * 赔付结清
     */
    private AllCompensateRule allCompensateRule;

    /**
     * 二次逾期还款
     */
    private OverdueRule againOverdueRule;

    /**
     * 二次单期代偿
     */
    private SingleCompensateRule againSingleCompensateRule;

    /**
     * 二次赔付结清
     */
    private AllCompensateRule againAllCompensateRule;
}
