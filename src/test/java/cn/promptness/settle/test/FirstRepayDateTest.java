package cn.promptness.settle.test;

import cn.promptness.settle.BaseTest;
import cn.promptness.settle.calculator.element.SettleDecimal;
import cn.promptness.settle.spring.CalculateContext;
import cn.promptness.settle.utils.CalculatorUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirstRepayDateTest extends BaseTest {

    @Autowired
    private CalculateContext calculateContext;

    // 还款日  - 出账日
    Map<Integer, Integer> identityHashMap = new HashMap<>();

    @Before
    public void init() {

        identityHashMap.put(1, 22);
        identityHashMap.put(2, 23);
        identityHashMap.put(3, 24);
        identityHashMap.put(4, 25);
        identityHashMap.put(5, 26);
        identityHashMap.put(6, 27);
        identityHashMap.put(7, 28);
        identityHashMap.put(8, 28);
        identityHashMap.put(9, 28);
        identityHashMap.put(10, 28);
        identityHashMap.put(11, 1);
        identityHashMap.put(12, 2);
        identityHashMap.put(13, 3);
        identityHashMap.put(14, 4);
        identityHashMap.put(15, 5);
        identityHashMap.put(16, 6);
        identityHashMap.put(17, 7);
        identityHashMap.put(18, 8);
        identityHashMap.put(19, 9);
        identityHashMap.put(20, 10);
        identityHashMap.put(21, 11);
        identityHashMap.put(22, 12);
        identityHashMap.put(23, 13);
        identityHashMap.put(24, 14);
        identityHashMap.put(25, 15);
        identityHashMap.put(26, 16);
        identityHashMap.put(27, 17);
        identityHashMap.put(28, 18);

    }

    @Test
    public void test() {


        StandardEvaluationContext context = calculateContext.newContext();


        Date date = new Date(Timestamp.valueOf("2021-01-01 00:00:00").getTime());
        for (int i = 0; i < 365; i++) {

            Date pd = DateUtils.addDays(date, i);


            // 还款日  - 出账日
            for (Map.Entry<Integer, Integer> entry : identityHashMap.entrySet()) {

                context.setVariable("RDN", entry.getKey()); // 固定还款日
                context.setVariable("BDN", entry.getValue()); // 出账日
                context.setVariable("PD", pd); // 放款成功日期


                String frd = "#FRD_FUNCTION(#RDN,#BDN,#PD)";
                Date firstRepayDate = CalculatorUtil.parseRule(frd, Date.class, context);

                context.setVariable("FRD", firstRepayDate); // 首期应还款日

                String fs = "#DAYS_FUNCTION(#PD,#FRD)";
                SettleDecimal settleDecimal = CalculatorUtil.parseRule(fs, SettleDecimal.class, context);

                // 首期区间天数
                System.out.println(
                        "放款日:" + new SimpleDateFormat("yyyy-MM-dd").format(pd)
                                + "\t账单日:" + entry.getValue()
                                + "\t还款日:" + entry.getKey()
                                + "\t首期应还款日:" + new SimpleDateFormat("yyyy-MM-dd").format(firstRepayDate)
                                + "\t间隔天数:" + settleDecimal.intValue()
                );

            }


        }


    }

}
