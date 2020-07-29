package cn.promptness.settle.rule.settle.payoff;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BasePayoffRule extends AbstractRule {


    @ContextRule(desc = "当天借款当天提前结清")
    private PayoffRule onPaymentDatePayoffRule;


    @ContextRule(desc = "尾期应还款日前两天提前结清")
    private PayoffRule twoBetweenLastRepayDatePayoffRule;

    @ContextRule(desc = "尾期应还款日前一天提前结清")
    private PayoffRule oneBetweenLastRepayDatePayoffRule;

    @ContextRule(desc = "尾期应还款日当天提前结清")
    private PayoffRule onLastRepayDatePayoffRule;

    @ContextRule(desc = "尾期应还款日前（包含）提前结清")
    private PayoffRule lastTermPayoffRule;


    @ContextRule(desc = "应还款日前两天提前结清")
    private PayoffRule twoBetweenRepayDatePayoffRule;

    @ContextRule(desc = "应还款日前一天提前结清")
    private PayoffRule oneBetweenRepayDatePayoffRule;

    @ContextRule(desc = "应还款日当天提前结清")
    private PayoffRule onRepayDatePayoffRule;

    @ContextRule(desc = "应还款日前（包含）提前结清")
    private PayoffRule payoffRule;

}
