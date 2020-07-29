package cn.promptness.settle.service;

import cn.promptness.settle.domain.PayOrder;
import cn.promptness.settle.rule.interest.BaseInterestRule;
import cn.promptness.settle.BaseTest;
import cn.promptness.settle.calculator.element.SettleDecimal;
import cn.promptness.settle.domain.CapitalExpectRepay;
import cn.promptness.settle.rule.BaseRuleInfo;
import cn.promptness.settle.rule.base.BaseInfoRule;
import cn.promptness.settle.rule.base.PrecisionInfoRule;
import cn.promptness.settle.rule.base.RateInfoRule;
import cn.promptness.settle.rule.interest.FirstInterestRule;
import cn.promptness.settle.rule.interest.LastInterestRule;
import cn.promptness.settle.rule.interest.MiddleInterestRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ExpectRepayServiceTest extends BaseTest {

    @Autowired
    private ExpectRepayService expectRepayService;

    @Test
    public void listCapitalExpectRepay() {


        PayOrder payOrder = new PayOrder();
        payOrder.setAssetId("1120061116314761160278");
        payOrder.setLoanTerm(new SettleDecimal(24));
        payOrder.setLoanAmount(new SettleDecimal(109));
        payOrder.setPaymentTime(new Date(Timestamp.valueOf("2020-06-11 16:39:27").getTime()));
        payOrder.setFixedRepayDay(new SettleDecimal(14));
        payOrder.setFixedBillDay(new SettleDecimal(4));
        payOrder.setApplyPayDate(new Date(Timestamp.valueOf("2020-06-11 16:24:41").getTime()));


        // 当前订单结算规则（mock）
        BaseRuleInfo baseRuleInfo = getBaseRuleInfo(payOrder);

        List<CapitalExpectRepay> capitalExpectRepayList = expectRepayService.listCapitalExpectRepay(payOrder, baseRuleInfo);

        System.out.println(toJSON(capitalExpectRepayList));
    }


    private BaseRuleInfo getBaseRuleInfo(PayOrder payOrder) {

        BaseInterestRule baseInterestRule = new BaseInterestRule();
        baseInterestRule.setFirstInterestRule(getFirstInterestRule());
        baseInterestRule.setMiddleInterestRule(getMiddleInterestRule());
        baseInterestRule.setLastInterestRule(getLastInterestRule());


        BaseRuleInfo baseRuleInfo = new BaseRuleInfo();
        baseRuleInfo.setBaseInfoRule(getBaseInfoRule());
        baseRuleInfo.setBaseInterestRule(baseInterestRule);

        return baseRuleInfo;
    }

    private LastInterestRule getLastInterestRule() {
        LastInterestRule lastInterestRule = new LastInterestRule();
        lastInterestRule.setLastRepayDateRule("#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))");
        lastInterestRule.setLastPrincipalAmountRule("#NPA");
        lastInterestRule.setLastInterestAmountRule("#NPA.multiply(#MR ,#RP)");
        lastInterestRule.setLastTotalAmountRule("#LPA.add(#LIA)");
        lastInterestRule.setMonthAmountRule("#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)) .divide(#MR.add(1).pow(#LM).subtract(1),#RP) ");
        return lastInterestRule;
    }

    private MiddleInterestRule getMiddleInterestRule() {
        MiddleInterestRule middleInterestRule = new MiddleInterestRule();
        middleInterestRule.setMiddleRepayDateRule("#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))");
        middleInterestRule.setMiddlePrincipalAmountRule("#MA.subtract(#NPA.multiply(#MR),#RP)");
        middleInterestRule.setMiddleInterestAmountRule("#NPA.multiply(#MR,#RP)");
        middleInterestRule.setMiddleTotalAmountRule("#MA");
        middleInterestRule.setMonthAmountRule("#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)) .divide(#MR.add(1).pow(#LM).subtract(1),#RP) ");
        return middleInterestRule;
    }

    private FirstInterestRule getFirstInterestRule() {
        FirstInterestRule firstInterestRule = new FirstInterestRule();
        firstInterestRule.setFirstStartDateRule("#PD");
        firstInterestRule.setFirstPrincipalAmountRule("#MA.subtract(#LA.multiply(#MR),#RP)");
        firstInterestRule.setFirstInterestAmountRule("#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)");
        firstInterestRule.setFirstTotalAmountRule("#FPA.add(#FIA)");
        firstInterestRule.setMonthAmountRule("#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RP)");
        firstInterestRule.setFirstRepayDateRule("#FRD_FUNCTION(#RDN,#BDN,#PD)");
        return firstInterestRule;
    }


    private BaseInfoRule getBaseInfoRule() {
        PrecisionInfoRule precisionInfoRule = new PrecisionInfoRule();
        precisionInfoRule.setResultPrecisionRule("@precision.init(2,'HALF_UP')");
        precisionInfoRule.setProcessPrecisionRule("@precision.init(8,'HALF_UP')");
        precisionInfoRule.setMonthRatePrecisionRule("@precision.init(12,'HALF_UP')");
        precisionInfoRule.setDayRatePrecisionRule("@precision.init(12,'HALF_UP')");


        RateInfoRule rateInfoRule = new RateInfoRule();
        rateInfoRule.setYearRateRule("0.09");
        rateInfoRule.setMonthOfYearRateRule("#YR.divide(12,#MRP)");
        rateInfoRule.setDayOfYearRateRule("#YR.divide(360,#DRP)");
        rateInfoRule.setMulctRateRule("#YR.multiply(1.5)");
        rateInfoRule.setMonthOfMulctRateRule("#MYR.divide(12,#MRP)");
        rateInfoRule.setDayOfMulctRateRule("#MYR.divide(360,#DRP)");

        BaseInfoRule baseInfoRule = new BaseInfoRule();
        baseInfoRule.setPrecisionInfoRule(precisionInfoRule);
        baseInfoRule.setRateInfoRule(rateInfoRule);
        return baseInfoRule;
    }
}