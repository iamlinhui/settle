package cn.promptness.settle.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资金方预计还款明细
 */
@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CapitalExpectRepayVo {

    /**
     * 资金方还款利率
     */
    private String repayRate;
    /**
     * 资金方罚息利率
     */
    private String mulctRate;

    /**
     * 资金占用费费率
     */
    private String repayOccupyRate;

    /**
     * 资金方赔付利率
     */
    private String compensateRate;

    /**
     * 还款期数
     */
    private String repayTerm;

    /**
     * 还款日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date repayDate;
    /**
     * 还款总额
     */
    private String repayTotal;
    /**
     * 还款本金
     */
    private String repayPrincipal;
    /**
     * 还款利息
     */
    private String repayFee;

    /**
     * 未还本金
     */
    private String notRepayPrincipal;


    /**
     * 间隔天数
     */
    private String betweenDays;

}
