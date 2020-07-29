package cn.promptness.settle.rule;

import cn.promptness.settle.rule.base.BaseInfoRule;
import cn.promptness.settle.rule.interest.BaseInterestRule;
import cn.promptness.settle.rule.settle.BaseSettleRule;
import lombok.Data;

/**
 * 结算规则对象
 *
 * @author lynn
 * @date 2020/6/16 14:42
 * @since v1.0.0
 */
@Data
public class BaseRuleInfo {

    /**
     * 基础信息 （利率和精度）
     */
    private BaseInfoRule baseInfoRule;

    /**
     * 还款计划计算信息
     */
    private BaseInterestRule baseInterestRule;

    /**
     * 实还计算信息
     */
    private BaseSettleRule baseSettleRule;
}
