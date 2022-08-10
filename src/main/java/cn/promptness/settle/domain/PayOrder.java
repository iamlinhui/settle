package cn.promptness.settle.domain;

import cn.promptness.settle.annotation.ContextField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单信息
 */
@Setter
@Getter
public class PayOrder {

    /**
     * 渠道号
     */
    private Integer loanChannelId;

    /**
     * 第三方渠道号
     */
    private String thirdChannelId;

    /**
     * 资产号
     */
    private String assetId;
    /**
     * 订单号
     */
    private String cusOrderId;
    /**
     * 资金方订单号
     */
    private String capitalOrderId;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 子订单类型
     */
    private Integer subOrderType;
    /**
     * 三级订单类型
     */
    private Integer saleType;

    /**
     * 借款期数
     */
    @ContextField(value = "LM")
    private Integer loanTerm;

    /**
     * 借款金额
     */
    @ContextField(value = "LA")
    private BigDecimal loanAmount;

    /**
     * 借款日期
     */
    @ContextField(value = "PD")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentTime;


    /**
     * 前端利率
     */
    @ContextField(value = "CR")
    private BigDecimal cusRate;

    /**
     * 前端优惠后利率
     */
    @ContextField(value = "AR")
    private BigDecimal annualRate;


    /**
     * 固定还款日
     */
    @ContextField(value = "RDN")
    private BigDecimal fixedRepayDay;

    /**
     * 出账日
     */
    @ContextField(value = "BDN")
    private BigDecimal fixedBillDay;

    /**
     * 放款申请时间
     */
    @ContextField(value = "LD")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyPayDate;


}
