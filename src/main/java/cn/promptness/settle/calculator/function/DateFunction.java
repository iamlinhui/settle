package cn.promptness.settle.calculator.function;

import cn.promptness.settle.annotation.CalculatorFunction;
import cn.promptness.settle.calculator.element.SettleDecimal;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author lynn
 * @date 2020/5/26 10:29
 * @since v1.0.0
 */
@Component
public class DateFunction {

    @CalculatorFunction(value = "SUM")
    public static Number sumList(List<Number> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0L;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Number number : list) {
            sum = sum.add(new BigDecimal(number.toString()));
        }
        return sum;
    }

    /**
     * 1. 还款日 <  账单日 <= 放款日  ==>还款日在下下月
     * 2. 还款日 >  账单日  >  放款日  ==>还款日在当月
     * 3. 其他  ==>还款日在下月
     */
    @CalculatorFunction("FRD_FUNCTION")
    public static Date getFirstRepayDate(Integer repayDay, Integer billDay, Date businessDate) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(DateUtils.truncate(businessDate, Calendar.DATE));

        int gavinDay = instance.get(Calendar.DATE);
        instance.set(Calendar.DATE, repayDay);

        if (repayDay < billDay && billDay <= gavinDay) {
            instance.add(Calendar.MONTH, 2);
            return instance.getTime();
        }

        if (repayDay > billDay && billDay > gavinDay) {
            return instance.getTime();
        }

        instance.add(Calendar.MONTH, 1);
        return instance.getTime();
    }

    @CalculatorFunction("DAYS_FUNCTION")
    public static SettleDecimal daysBetween(Date startDate, Date endDate) {
        long timeS = DateUtils.truncate(startDate, Calendar.DATE).getTime();
        long timeE = DateUtils.truncate(endDate, Calendar.DATE).getTime();
        long between = timeE - timeS;
        long days = between / (1000 * 3600 * 24);
        return new SettleDecimal(String.valueOf(days));
    }

    @CalculatorFunction("MONTH_ADD_FUNCTION")
    public static Date addMonths(Date startDate, Integer add) {
        return DateUtils.addMonths(startDate, add);
    }

    @CalculatorFunction("DAY_ADD_FUNCTION")
    public static Date addDays(Date startDate, Integer add) {
        return DateUtils.addDays(startDate, add);
    }
}
