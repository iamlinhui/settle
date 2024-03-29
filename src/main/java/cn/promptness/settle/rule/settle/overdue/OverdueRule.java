package cn.promptness.settle.rule.settle.overdue;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.settle.AbstractSettleRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author lynn
 * @date 2020/6/12 18:06
 * @since v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OverdueRule extends AbstractSettleRule {

    @ContextRule(value = "RMA", desc = "实还罚息", clazz = BigDecimal.class)
    private String realRepayMulctAmountRule;

}
