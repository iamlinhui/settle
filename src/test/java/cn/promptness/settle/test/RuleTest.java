package cn.promptness.settle.test;

import cn.promptness.settle.rule.base.PrecisionInfoRule;
import cn.promptness.settle.rule.interest.FirstInterestRule;
import cn.promptness.settle.rule.interest.LastInterestRule;
import cn.promptness.settle.rule.interest.MiddleInterestRule;
import cn.promptness.settle.spring.CalculateContext;
import cn.promptness.settle.utils.CalculatorUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.promptness.settle.BaseTest;
import cn.promptness.settle.calculator.element.SettleDecimal;
import cn.promptness.settle.rule.base.BaseInfoRule;
import cn.promptness.settle.rule.base.RateInfoRule;
import cn.promptness.settle.rule.settle.BaseSettleRule;
import cn.promptness.settle.rule.settle.compensate.AllCompensateRule;
import cn.promptness.settle.rule.settle.compensate.SingleCompensateRule;
import cn.promptness.settle.rule.settle.normal.NormalRule;
import cn.promptness.settle.rule.settle.overdue.OverdueRule;
import cn.promptness.settle.rule.settle.payoff.BasePayoffRule;
import cn.promptness.settle.rule.settle.payoff.PayoffRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class RuleTest extends BaseTest {

    @Autowired
    private CalculateContext calculateContext;

    @Test
    public void ruleTest() {

        StandardEvaluationContext context = calculateContext.newContext();

        // 填充基本信息
        BaseInfoRule baseInfoRule = getBaseInfoRule();
        CalculatorUtil.parseRuleToContext(context, baseInfoRule);


        // 首期规则
        FirstInterestRule firstInterestRule = getFirstInterestRule();
        // 中间期规则
        MiddleInterestRule middleInterestRule = getMiddleInterestRule();
        // 尾期规则
        LastInterestRule lastInterestRule = getLastInterestRule();


        // mock值1120061116314761160278
        context.setVariable("LA", new SettleDecimal("109"));
        context.setVariable("LM", new SettleDecimal("24"));
        context.setVariable("BDN", new SettleDecimal("4"));
        context.setVariable("RDN", new SettleDecimal("14"));

        context.setVariable("LD", new Date(Timestamp.valueOf("2020-06-11 16:24:41").getTime()));
        context.setVariable("PD", new Date(Timestamp.valueOf("2020-06-11 16:39:27").getTime()));


        int lm = Integer.parseInt(Objects.requireNonNull(context.lookupVariable("LM")).toString());
        for (int i = 1; i <= lm; i++) {

            context.setVariable("CLM", new SettleDecimal(i));
            System.out.print("第" + i + "期\t");

            if (i == 1) {
                CalculatorUtil.parseRuleToContext(context, firstInterestRule);

                System.out.print("本金" + context.lookupVariable("FPA") + "\t");
                System.out.print("利息" + context.lookupVariable("FIA") + "\t");
                System.out.print("总额" + context.lookupVariable("FTA") + "\t");
                System.out.println("还款日" + JSON.toJSONStringWithDateFormat(context.lookupVariable("FRD"), "yyyy-MM-dd"));

                continue;
            }

            if (i == lm) {
                CalculatorUtil.parseRuleToContext(context, lastInterestRule);
                System.out.print("本金" + context.lookupVariable("LPA") + "\t");
                System.out.print("利息" + context.lookupVariable("LIA") + "\t");
                System.out.print("总额" + context.lookupVariable("LTA") + "\t");
                System.out.println("还款日" + JSON.toJSONStringWithDateFormat(context.lookupVariable("MRD"), "yyyy-MM-dd"));
                continue;
            }

            CalculatorUtil.parseRuleToContext(context, middleInterestRule);
            System.out.print("本金" + context.lookupVariable("MPA") + "\t");
            System.out.print("利息" + context.lookupVariable("MIA") + "\t");
            System.out.print("总额" + context.lookupVariable("MTA") + "\t");
            System.out.println("还款日" + JSON.toJSONStringWithDateFormat(context.lookupVariable("MRD"), "yyyy-MM-dd"));

        }

    }

    @Test
    public void realCalc() {
        BaseSettleRule baseSettleRule = getBaseSettleRule();
        System.out.println(JSON.toJSONString(baseSettleRule, SerializerFeature.DisableCircularReferenceDetect));
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


    /**
     * #NORMAL_REPAY  正常还款 #NRT
     * #ALL_PREPAY    提前结清 #ART
     * #COMPENSATION  赔付 #CRT
     * #OVERDUE_REPAY   逾期还款 #ORT
     */
    private BaseSettleRule getBaseSettleRule() {

        NormalRule normalRule = getNormalRule();


        BasePayoffRule basePayoffRule = getBasePayoffRule();


        OverdueRule overdueRule = getOverdueRule();


        SingleCompensateRule singleCompensateRule = new SingleCompensateRule();
        singleCompensateRule.setOverdueRule(overdueRule);


        AllCompensateRule allCompensateRule = new AllCompensateRule();
        allCompensateRule.setSingleCompensateRule(singleCompensateRule);
        allCompensateRule.setBasePayoffRule(basePayoffRule);


        BaseSettleRule baseSettleRule = new BaseSettleRule();
        baseSettleRule.setNormalRule(normalRule);
        baseSettleRule.setBasePayoffRule(basePayoffRule);
        baseSettleRule.setOverdueRule(overdueRule);
        baseSettleRule.setSingleCompensateRule(singleCompensateRule);
        baseSettleRule.setAllCompensateRule(allCompensateRule);


        return baseSettleRule;


    }

    private OverdueRule getOverdueRule() {
        OverdueRule overdueRule = new OverdueRule();
        overdueRule.setRealRepayMulctAmountRule("#DAYS_FUNCTION(#ERD,#NOW).add(1) > 3 ? #EPA.multiply(#MDR).multiply(#DAYS_FUNCTION(#ERD,#NOW).add(1),#RR).min(#EPA) : 0");
        overdueRule.setRealRepayPrincipalAmountRule("#EPA");
        overdueRule.setRealRepayInterestAmountRule("#EIA");
        overdueRule.setRealRepayTypeRule("#DAYS_FUNCTION(#ERD,#NOW).add(1) > 3 ? #ORT : #NRT");
        overdueRule.setRealRepayDateRule("#DAY_ADD_FUNCTION(#NOW,1)");
        overdueRule.setRealSettleDateRule("#DAY_ADD_FUNCTION(#NOW,1)");
        return overdueRule;
    }

    private NormalRule getNormalRule() {
        NormalRule normalRule = new NormalRule();
        normalRule.setRealRepayPrincipalAmountRule("#EPA");
        normalRule.setRealRepayInterestAmountRule("#EIA");
        normalRule.setRealRepayTypeRule("#NRT");
        normalRule.setRealRepayDateRule("#DAY_ADD_FUNCTION(#ERD,1)");
        normalRule.setRealSettleDateRule("#ERD");
        return normalRule;
    }

    private BasePayoffRule getBasePayoffRule() {

        PayoffRule payoffRule = new PayoffRule();
        payoffRule.setRealRepayPrincipalAmountRule("#EPA");
        payoffRule.setRealRepayInterestAmountRule("#ENA.multiply(#DR).multiply(#DAYS_FUNCTION(#ESD,#NOW).add(1),#RP)");
        payoffRule.setRealRepayTypeRule("#ART");
        payoffRule.setRealRepayDateRule("#DAY_ADD_FUNCTION(#NOW,1)");
        payoffRule.setRealSettleDateRule("#DAY_ADD_FUNCTION(#NOW,1)");


        PayoffRule onRepayDatePayoffRule = new PayoffRule();
        onRepayDatePayoffRule.setNextRealRepayPrincipalAmountRule("NEPA");
        onRepayDatePayoffRule.setNextRealRepayInterestAmountRule("#NENA.multiply(#DR).multiply(#DAYS_FUNCTION(#ESD,#NOW).add(1),#RP)");
        onRepayDatePayoffRule.setNextRealRepayTypeRule("#ART");
        onRepayDatePayoffRule.setNextRealRepayDateRule("#DAY_ADD_FUNCTION(#NOW,1)");
        onRepayDatePayoffRule.setNextRealSettleDateRule("#DAY_ADD_FUNCTION(#NOW,1)");

        payoffRule.setRealRepayPrincipalAmountRule("#EPA");
        payoffRule.setRealRepayInterestAmountRule("#EIA");
        payoffRule.setRealRepayTypeRule("#NRT");
        payoffRule.setRealRepayDateRule("#DAY_ADD_FUNCTION(#NOW,1)");
        payoffRule.setRealSettleDateRule("#DAY_ADD_FUNCTION(#NOW,1)");


        BasePayoffRule basePayoffRule = new BasePayoffRule();
        basePayoffRule.setOnRepayDatePayoffRule(onRepayDatePayoffRule);
        basePayoffRule.setPayoffRule(payoffRule);
        return basePayoffRule;
    }
}
