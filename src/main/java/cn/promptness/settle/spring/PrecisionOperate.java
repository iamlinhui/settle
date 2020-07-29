package cn.promptness.settle.spring;

import cn.promptness.settle.calculator.element.Precision;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

/**
 * @author lynn
 * @date 2020/6/7 15:06
 * @since v1.0.0
 */
@Component("precision")
public class PrecisionOperate {

    /**
     * eg:@precision.init(8,'HALF_UP')
     *
     * @param scale        精度
     * @param roundingMode 保留策略
     * @return 自定义精度
     */
    public Precision init(int scale, RoundingMode roundingMode) {
        return new Precision(scale, roundingMode);
    }

}
