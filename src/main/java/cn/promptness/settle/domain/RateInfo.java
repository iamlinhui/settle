package cn.promptness.settle.domain;

import cn.promptness.settle.annotation.ContextValue;
import cn.promptness.settle.calculator.element.SettleDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateInfo {

    /**
     * 资金方还款利率
     */
    @ContextValue("YR")
    private SettleDecimal repayRate;
    /**
     * 资金方罚息利率
     */
    @ContextValue("MYR")
    private SettleDecimal mulctRate;

    /**
     * 资金占用费费率
     */
    @ContextValue("OYR")
    private SettleDecimal repayOccupyRate;

    /**
     * 资金方赔付利率
     */
    @ContextValue("CYR")
    private SettleDecimal compensateRate;

}
