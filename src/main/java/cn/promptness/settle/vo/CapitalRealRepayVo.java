package cn.promptness.settle.vo;

import cn.promptness.settle.enums.RepayType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 资金方实际还款明细
 */
@Setter
@Getter
public class CapitalRealRepayVo extends CapitalExpectRepayVo {


    /**
     * 罚息
     */
    private Long repayMulct;

    /**
     * 本金罚息
     */
    private Long repayPrincipalMulct;

    /**
     * 利息罚息
     */
    private Long repayFeeMulct;

    /**
     * 还款类型
     */
    private RepayType repayType;


    /**
     * 计息日期
     */
    private Date settleDate;

    /**
     * 提前结清违约金
     */
    private Long repayPenalty;


    /**
     * 资金占用费
     */
    private Long repayOccupy;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

}
