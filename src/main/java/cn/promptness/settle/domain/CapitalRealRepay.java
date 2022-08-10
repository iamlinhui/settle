package cn.promptness.settle.domain;

import cn.promptness.settle.enums.RepayType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金方实际还款明细
 */
@Setter
@Getter
public class CapitalRealRepay extends CapitalExpectRepay {


    /**
     * 罚息
     */
    private BigDecimal repayMulct;

    /**
     * 还款类型
     */
    private RepayType repayType;


    /**
     * 计息日期
     */
    private Date settleDate;

    /**
     * 资金占用费
     */
    private BigDecimal repayOccupy;

}
