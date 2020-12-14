package cn.promptness.settle.test;

import cn.promptness.settle.BaseTest;
import cn.promptness.settle.calculator.element.Precision;
import cn.promptness.settle.calculator.function.DateFunction;
import cn.promptness.settle.spring.CalculateContext;
import cn.promptness.settle.utils.CalculatorUtil;
import cn.promptness.settle.calculator.element.SettleDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class SpringTest extends BaseTest {

    @Autowired
    private CalculateContext calculateContext;

    @Test
    public void testInterest() {

        // "assetId":"1120052610064142672432"
        StandardEvaluationContext context = calculateContext.newContext();

        context.setVariable("LA", new SettleDecimal("100")); // 借款金额
        context.setVariable("NPR", new SettleDecimal("100")); //未还本金
        context.setVariable("LM", new SettleDecimal("12")); // 借款期数

        context.setVariable("LD", new Date(Timestamp.valueOf("2020-05-22 00:00:00").getTime())); // 借款日期
        context.setVariable("PD", new Date(Timestamp.valueOf("2020-05-26 00:00:00").getTime())); // 放款成功日期

        context.setVariable("RDN", 13); // 固定还款日
        context.setVariable("BDN", 3); // 出账日

        context.setVariable("YR", new SettleDecimal("0.072")); //年华利率

        context.setVariable("RS", new Precision(2, RoundingMode.HALF_UP)); //结果精度
        context.setVariable("PR", new Precision(9, RoundingMode.HALF_UP)); //过程精度


        String mr = "#YR.divide(12,#PR)";
        String dr = "#YR.divide(360,#PR)";
        String ma = "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RS)";

        context.setVariable("MR", CalculatorUtil.parseRule(mr, SettleDecimal.class, context)); // 月利率
        context.setVariable("DR", CalculatorUtil.parseRule(dr, SettleDecimal.class, context)); // 日利率
        context.setVariable("MA", CalculatorUtil.parseRule(ma, SettleDecimal.class, context)); // 月供

        String frd = "#FRD_FUNCTION(#RDN,#BDN,#PD)";
        context.setVariable("FRD", CalculatorUtil.parseRule(frd, Date.class, context));  // 首期还款日

        String fsd = "#PD";
        context.setVariable("FSD", CalculatorUtil.parseRule(fsd, Date.class, context));  // 起息日

        BigDecimal lm = (BigDecimal) context.lookupVariable("LM");

        for (Integer i = 1; i <= lm.intValue(); i++) {

            // 首期
            if (Objects.equals(i, 1)) {

                // FPM 首期本金
                String fpm = "#MA.subtract(#NPR.multiply(#MR),#RS)";
                context.setVariable("FPM", CalculatorUtil.parseRule(fpm, SettleDecimal.class, context));  // 首期本金

                //FIM 首期利息
                String FIM = "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RS)";
                context.setVariable("FIM", CalculatorUtil.parseRule(FIM, SettleDecimal.class, context));  // 首期利息

                // FIT 首期月供
                String fit = "#FPM.add(#FIM)";
                context.setVariable("FIT", CalculatorUtil.parseRule(fit, SettleDecimal.class, context));  // 首期月供

                context.setVariable("NPR", CalculatorUtil.parseRule("#NPR.subtract(#FPM)", SettleDecimal.class, context)); //未还本金


                String form = "第%s期,当期本金%s,当期利息%s,当期月供%s,还款日期%s";
                logger.info(String.format(form, i, context.lookupVariable("FPM"), context.lookupVariable("FIM"), context.lookupVariable("FIT"), new SimpleDateFormat("yyyy-MM-dd").format(context.lookupVariable("FRD"))));

                continue;
            }


            // 尾期
            if (Objects.equals(i, lm.intValue())) {

                // LPM 尾期本金
                String LPM = "#NPR";
                context.setVariable("LPM", CalculatorUtil.parseRule(LPM, SettleDecimal.class, context));  // 尾期本金

                // LIM 尾期利息
                String LIM = "#NPR.multiply(#MR,#RS)";
                context.setVariable("LIM", CalculatorUtil.parseRule(LIM, SettleDecimal.class, context));

                // LPT 尾期月供
                String LPT = "#LPM.add(#LIM)";
                context.setVariable("LPT", CalculatorUtil.parseRule(LPT, SettleDecimal.class, context));


                Date frd1 = (Date) context.lookupVariable("FRD");
                Date date = DateFunction.addMonths(frd1, i - 1);

                String form = "第%s期,当期本金%s,当期利息%s,当期月供%s,还款日期%s";
                logger.info(String.format(form, i, context.lookupVariable("LPM"), context.lookupVariable("LIM"), context.lookupVariable("LPT"), new SimpleDateFormat("yyyy-MM-dd").format(date)));

                continue;
            }


            // MPM 中间期本金
            String mpm = "#MA.subtract(#NPR.multiply(#MR),#RS)";
            context.setVariable("MPM", CalculatorUtil.parseRule(mpm, SettleDecimal.class, context));  // 中间期本金

            // MIM 中间期利息
            String MIM = "#NPR.multiply(#MR ,#RS)";
            context.setVariable("MIM", CalculatorUtil.parseRule(MIM, SettleDecimal.class, context));

            // MIT 中间期月供
            String MIT = "#MA";
            context.setVariable("MIT", CalculatorUtil.parseRule(MIT, SettleDecimal.class, context));

            context.setVariable("NPR", CalculatorUtil.parseRule("#NPR.subtract(#MPM)", SettleDecimal.class, context)); //未还本金


            Date frd1 = (Date) context.lookupVariable("FRD");
            Date date = DateFunction.addMonths(frd1, i - 1);

            String form = "第%s期,当期本金%s,当期利息%s,当期月供%s,还款日期%s";

            logger.info(String.format(form, i, context.lookupVariable("MPM"), context.lookupVariable("MIM"), context.lookupVariable("MIT"), new SimpleDateFormat("yyyy-MM-dd").format(date)));

        }

    }

    @Test
    public void loop() {

        for (int i = 0; i < 10; i++) {
            StopWatch stopWatch = new StopWatch("testInterest");
            stopWatch.start();
            testInterest();
            stopWatch.stop();
            logger.info(stopWatch.prettyPrint());
        }

    }


    @Test
    public void testReal() {


        StandardEvaluationContext context = calculateContext.newContext();

        context.setVariable("YR", new SettleDecimal("0.24")); //年华利率

        context.setVariable("CYR", new SettleDecimal("0.36")); //结清利率

        context.setVariable("RS", new Precision(2, RoundingMode.HALF_UP)); //结果精度
        context.setVariable("PR", new Precision(9, RoundingMode.HALF_UP)); //过程精度

        String mr = "#YR.divide(12,#PR)";
        String dr = "#YR.divide(360,#PR)";
        context.setVariable("MR", CalculatorUtil.parseRule(mr, SettleDecimal.class, context)); // 月利率
        context.setVariable("DR", CalculatorUtil.parseRule(dr, SettleDecimal.class, context)); // 日利率


        // 结息日 #SED
        context.setVariable("SED", new SettleDecimal("1"));
        // 信息流 #INF
        context.setVariable("INF", new SettleDecimal("1"));


        //当期应还利息 EIA
        context.setVariable("EIA", new SettleDecimal("145.56"));

        //当期应还本金 EPA
        context.setVariable("EPA", new SettleDecimal("745.56"));


        //当期未还本金 ENA
        context.setVariable("ENA", new SettleDecimal("9345.27"));

        //当期应还款日 ERD
        context.setVariable("ERD", new Date(Timestamp.valueOf("2020-06-15 00:00:00").getTime()));

        // 当前还款时间 NOW
        context.setVariable("NOW", new Date(Timestamp.valueOf("2020-06-10 00:00:00").getTime()));

        //上一期应还款日 （起息日） ESD
        context.setVariable("ESD", new Date(Timestamp.valueOf("2020-05-23 00:00:00").getTime()));


        //  934未还本金按日计息5.27 * 19 * 0.24 / 360 = 118.37342
        String RID = "#ENA.multiply(#DR).multiply(#DAYS_FUNCTION(#ESD,#NOW).add(#SED),#RS)";
        logger.info(CalculatorUtil.parseRule(RID, SettleDecimal.class, context).toString());

        // 实际还款日 #RRD = #NOW + #INF

    }

    @Test
    public void testSpecialInterest() {


    }

    @Test
    public void testMulct() {
        StandardEvaluationContext context = calculateContext.newContext();

        context.setVariable("YR", new SettleDecimal("0.24")); //年华利率
        context.setVariable("MYR", CalculatorUtil.parseRule("#YR.multiply(1.5)", SettleDecimal.class, context)); //罚息利率

        context.setVariable("RS", new Precision(2, RoundingMode.HALF_UP)); //结果精度
        context.setVariable("PR", new Precision(9, RoundingMode.HALF_UP)); //过程精度

        String mdr = "#MYR.divide(360,#PR)";
        context.setVariable("MDR", CalculatorUtil.parseRule(mdr, SettleDecimal.class, context)); // 罚息日利率


        // 结息日 #SED
        context.setVariable("SED", new SettleDecimal("1"));
        // 信息流 #INF
        context.setVariable("INF", new SettleDecimal("1"));

        //当期应还利息 EIA
        context.setVariable("EIA", new SettleDecimal("145.56"));

        //当期应还本金 EPA
        context.setVariable("EPA", new SettleDecimal("745.56"));

        // 逾期本金
        context.setVariable("MPA", CalculatorUtil.parseRule("#EPA", SettleDecimal.class, context)); // 逾期本金

        // 当前还款时间 NOW
        context.setVariable("NOW", new Date(Timestamp.valueOf("3020-06-20 00:00:00").getTime()));

        //当期应还款日 ERD
        context.setVariable("ERD", new Date(Timestamp.valueOf("2020-06-15 00:00:00").getTime()));


        // 宽限期内 正常还款 罚息为0

        // 逾期本金按日计息  4.47336000000    745.56 * (0.24*1.5/360) * (5 + 1) = 4.47336

        // 3天宽限期
        System.out.println(CalculatorUtil.parseRule("#DAYS_FUNCTION(#ERD,#NOW).add(#SED) > 3 ? #MPA.multiply(#MDR).multiply(#DAYS_FUNCTION(#ERD,#NOW).add(#SED),#RS).min(#EPA) : 0", SettleDecimal.class, context));

    }

    @Test
    public void testPrecisionOperate() {

        StandardEvaluationContext context = calculateContext.newContext();

        String spel = "@precision.init(1,'UP')";

        System.out.println(CalculatorUtil.parseRule(spel, Precision.class, context));

    }


    @Test
    public void testPmt() {
        StandardEvaluationContext context = calculateContext.newContext();

        context.setVariable("YR", new SettleDecimal("0.068")); //年华利率
        context.setVariable("LA", new SettleDecimal("3000")); // 借款金额
        context.setVariable("LM", new SettleDecimal("3")); // 借款期数

        context.setVariable("RS", new Precision(12, RoundingMode.HALF_UP)); //结果精度
        context.setVariable("PR", new Precision(8, RoundingMode.HALF_UP)); //过程精度

        context.setVariable("MR", CalculatorUtil.parseRule("#YR.divide(12,#PR)", SettleDecimal.class, context)); // 月利率


        String ma = "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#PR).precision(#RS)";

        System.out.println(CalculatorUtil.parseRule(ma, SettleDecimal.class, context));
    }
}
