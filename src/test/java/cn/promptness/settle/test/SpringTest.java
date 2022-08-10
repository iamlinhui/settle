package cn.promptness.settle.test;

import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.common.element.Precision;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.promptness.settle.BaseTest;
import cn.promptness.settle.utils.CalculatorUtil;
import org.junit.Test;

import java.math.RoundingMode;


public class SpringTest extends BaseTest {

    @Test
    public void testPmt() {
        Context context = Lang.context();

        context.set("YR", 0.068); //年华利率
        context.set("LA", 1165); // 借款金额
        context.set("LM", 24); // 借款期数

        context.set("RS", new Precision(12, RoundingMode.HALF_UP)); //结果精度
        context.set("PR", new Precision(8, RoundingMode.HALF_UP)); //过程精度

        context.set("MR", CalculatorUtil.parseRule("YR.div(12,PR)", SettleDecimal.class, context)); // 月利率


        String ma = "LA.mul(MR).mul(MR.add(1).pow(LM)).div(MR.add(1).pow(LM).sub(1),PR).off(RS)";

        System.out.println(CalculatorUtil.parseRule(ma, SettleDecimal.class, context));
    }
}
