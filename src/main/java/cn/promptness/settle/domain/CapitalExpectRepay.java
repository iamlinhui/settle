package cn.promptness.settle.domain;

import cn.promptness.settle.annotation.ContextValue;
import cn.promptness.settle.calculator.element.SettleDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资金方预计还款明细
 */
@Setter
@Getter
public class CapitalExpectRepay extends RateInfo {

    /**
     * 还款期数
     */
    @ContextValue("CLM")
    private SettleDecimal repayTerm;

    /**
     * 还款日期
     */
    @ContextValue("CRD")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date repayDate;

    /**
     * 还款总额
     */
    @ContextValue("CTA")
    private SettleDecimal repayTotal;

    /**
     * 还款本金
     */
    @ContextValue("CPA")
    private SettleDecimal repayPrincipal;

    /**
     * 还款利息
     */
    @ContextValue("CIA")
    private SettleDecimal repayFee;


    /**
     * 间隔天数
     */
    @ContextValue("CDC")
    private SettleDecimal betweenDays;

    /**
     * 未还本金
     */
    @ContextValue("NPA")
    private SettleDecimal notRepayPrincipal;

}
