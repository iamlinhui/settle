package cn.promptness.settle.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 订单信息
 *
 * @author raisewang
 */
@Setter
@Getter
public class PayOrderVo {
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
    private Integer loanTerm;

    /**
     * 借款金额
     */
    private Long loanAmount;

    /**
     * 借款日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentTime;
    /**
     * 资金计划ID
     */
    private String capitalPlanId;

    private String loanName;


    /**
     * 前端利率
     */
    private Integer cusRate;

    /**
     * 前端优惠后利率
     */
    private Integer annualRate;


    /**
     * 固定还款日
     */
    private Integer fixedRepayDay;

    /**
     * 出账日
     */
    private Integer fixedBillDay;

    /**
     * 下单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;

    /**
     * 放款申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyPayDate;

    /**
     * 短贷参数-借款天数
     */
    private Integer loanDays;

    /**
     * 担保模式号
     */
    private String insureModeNo;

    /**
     * 还款方式
     */
    private Integer repayMode;

    /**
     * 只算利息期数
     */
    private Integer installmentsNumbers;

}
