package cn.promptness.settle.function;

import cn.holmes.settle.expression.common.annotation.HolmesFunction;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lynn
 * @date 2020/5/26 10:29
 * @since v1.0.0
 */
@Component
public class DateFunction {

    /**
     * 1. 还款日 <  账单日 <= 放款日  ==>还款日在下下月
     * 2. 还款日 >  账单日  >  放款日  ==>还款日在当月
     * 3. 其他  ==>还款日在下月
     */
    @HolmesFunction("FRD_FUNCTION")
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

    @HolmesFunction("DAYS_FUNCTION")
    public static Long daysBetween(Date startDate, Date endDate) {
        long timeS = DateUtils.truncate(startDate, Calendar.DATE).getTime();
        long timeE = DateUtils.truncate(endDate, Calendar.DATE).getTime();
        long between = timeE - timeS;
        return between / (1000 * 3600 * 24);
    }

    @HolmesFunction("MONTH_ADD_FUNCTION")
    public static Date addMonths(Date startDate, Integer add) {
        return DateUtils.addMonths(startDate, add);
    }

    @HolmesFunction("DAY_ADD_FUNCTION")
    public static Date addDays(Date startDate, Integer add) {
        return DateUtils.addDays(startDate, add);
    }
}
