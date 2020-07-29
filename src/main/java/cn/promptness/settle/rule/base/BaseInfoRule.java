package cn.promptness.settle.rule.base;

import cn.promptness.settle.annotation.ContextRule;
import cn.promptness.settle.rule.AbstractRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础信息
 *
 * @author lynn
 * @date 2020/6/11 14:47
 * @since v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInfoRule extends AbstractRule {

    @ContextRule(desc = "数据精度", order = 1)
    private PrecisionInfoRule precisionInfoRule;

    @ContextRule(desc = "基础利率配置信息", order = 2)
    private RateInfoRule rateInfoRule;

}
