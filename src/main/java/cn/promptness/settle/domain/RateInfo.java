package cn.promptness.settle.domain;

import cn.promptness.settle.annotation.ContextValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RateInfo {

    /**
     * 资金方还款利率
     */
    @ContextValue("YR")
    private BigDecimal repayRate;
    /**
     * 资金方罚息利率
     */
    @ContextValue("MYR")
    private BigDecimal mulctRate;

    /**
     * 资金占用费费率
     */
    @ContextValue("OYR")
    private BigDecimal repayOccupyRate;

    /**
     * 资金方赔付利率
     */
    @ContextValue("CYR")
    private BigDecimal compensateRate;

}
