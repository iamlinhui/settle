package cn.promptness.settle.vo;

import cn.promptness.settle.domain.PayOrder;
import cn.promptness.settle.rule.BaseRuleInfo;
import lombok.Data;

@Data
public class SettleVo {

    private PayOrder payOrder;

    private BaseRuleInfo rule;


}
