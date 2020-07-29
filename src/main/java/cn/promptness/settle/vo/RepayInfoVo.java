package cn.promptness.settle.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RepayInfoVo {

    /**
     * 资金方还款利率
     */
    private Long repayRate;
    /**
     * 资金方罚息利率
     */
    private Long mulctRate;

    /**
     * 资金占用费费率
     */
    private Long repayOccupyRate;

    /**
     * 资金方赔付利率
     */
    private Long compensateRate;


    // ====

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
     * 三级子订单类型
     */
    private Integer saleType;
    /**
     * 借款期数
     */
    private Integer loanTerm;
    /**
     * 借款金额
     */
    private Long loanAmount;
    /**
     * 借款日期
     */
    private Date paymentTime;


    /**
     * 付款申请时间
     */
    private Date applyPayDate;

}
