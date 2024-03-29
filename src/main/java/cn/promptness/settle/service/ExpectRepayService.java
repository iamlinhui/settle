package cn.promptness.settle.service;

import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.common.context.Context;
import cn.promptness.settle.domain.CapitalExpectRepay;
import cn.promptness.settle.domain.PayOrder;
import cn.promptness.settle.rule.BaseRuleInfo;
import cn.promptness.settle.rule.interest.BaseInterestRule;
import cn.promptness.settle.utils.CalculatorUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lynn
 * @date 2020/6/23 20:08
 * @since v1.0.0
 */
@Slf4j
@Service
public class ExpectRepayService {

    public List<CapitalExpectRepay> listCapitalExpectRepay(PayOrder payOrder, BaseRuleInfo baseRuleInfo) {

        Context context = Lang.context();

        // 填充订单信息
        CalculatorUtil.parseFieldToContext(context, payOrder);

        // 计算填充基础信息
        CalculatorUtil.parseRuleToContext(context, baseRuleInfo.getBaseInfoRule());

        // 还款计划计算规则
        BaseInterestRule baseInterestRule = baseRuleInfo.getBaseInterestRule();

        List<CapitalExpectRepay> capitalExpectRepayList = Lists.newArrayList();
        int loanTerm = payOrder.getLoanTerm();
        for (int i = 1; i <= loanTerm; i++) {
            // 设置当前期参数
            context.set("CLM", i);

            CapitalExpectRepay capitalExpectRepay = new CapitalExpectRepay();

            if (i == 1) {
                CalculatorUtil.parseRuleToContext(context, baseInterestRule.getFirstInterestRule());
            } else if (i < loanTerm) {
                CalculatorUtil.parseRuleToContext(context, baseInterestRule.getMiddleInterestRule());
            } else {
                CalculatorUtil.parseRuleToContext(context, baseInterestRule.getLastInterestRule());
            }

            // 从上下文取值 填充到对象中
            CalculatorUtil.parseContextToValue(context, capitalExpectRepay);
            capitalExpectRepayList.add(capitalExpectRepay);
        }


        return capitalExpectRepayList;
    }

}
