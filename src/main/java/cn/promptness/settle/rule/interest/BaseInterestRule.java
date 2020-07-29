package cn.promptness.settle.rule.interest;

import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInterestRule extends AbstractRule {

    private FirstInterestRule firstInterestRule;

    private MiddleInterestRule middleInterestRule;

    private LastInterestRule lastInterestRule;
}
