package cn.promptness.settle.domain;

import cn.promptness.settle.annotation.ContextValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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
    private Integer repayTerm;

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
    private BigDecimal repayTotal;

    /**
     * 还款本金
     */
    @ContextValue("CPA")
    private BigDecimal repayPrincipal;

    /**
     * 还款利息
     */
    @ContextValue("CIA")
    private BigDecimal repayFee;


    /**
     * 间隔天数
     */
    @ContextValue("CDC")
    private Integer betweenDays;

    /**
     * 未还本金
     */
    @ContextValue("NPA")
    private BigDecimal notRepayPrincipal;

}
