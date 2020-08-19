package cn.promptness.settle.calculator.element;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * @author lynn
 * @date 2020/5/23 13:31
 * @since v1.0.0
 */
public class SettleDecimal extends BigDecimal {

    public SettleDecimal(char[] in, int offset, int len) {
        super(in, offset, len);
    }

    public SettleDecimal(char[] in, int offset, int len, MathContext mc) {
        super(in, offset, len, mc);
    }

    public SettleDecimal(char[] in) {
        super(in);
    }

    public SettleDecimal(char[] in, MathContext mc) {
        super(in, mc);
    }

    public SettleDecimal(String val) {
        super(val);
    }

    public SettleDecimal(String val, MathContext mc) {
        super(val, mc);
    }

    public SettleDecimal(double val) {
        super(val);
    }

    public SettleDecimal(double val, MathContext mc) {
        super(val, mc);
    }

    public SettleDecimal(BigInteger val) {
        super(val);
    }

    public SettleDecimal(BigInteger val, MathContext mc) {
        super(val, mc);
    }

    public SettleDecimal(BigInteger unscaledVal, int scale) {
        super(unscaledVal, scale);
    }

    public SettleDecimal(BigInteger unscaledVal, int scale, MathContext mc) {
        super(unscaledVal, scale, mc);
    }

    public SettleDecimal(int val) {
        super(val);
    }

    public SettleDecimal(int val, MathContext mc) {
        super(val, mc);
    }

    public SettleDecimal(long val) {
        super(val);
    }

    public SettleDecimal(long val, MathContext mc) {
        super(val, mc);
    }

    public SettleDecimal(BigDecimal bigDecimal) {
        super(bigDecimal.toString());
    }

    // 除

    public SettleDecimal divide(SettleDecimal divisor, Precision precision) {
        return new SettleDecimal(this.divide(divisor, precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal divide(Integer divisor, Precision precision) {
        return new SettleDecimal(this.divide(new BigDecimal(divisor.toString()), precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal divide(Double divisor, Precision precision) {
        return new SettleDecimal(this.divide(new BigDecimal(divisor.toString()), precision.getScale(), precision.getRoundingMode()));
    }

    //乘

    public SettleDecimal multiply(SettleDecimal multiplicand, Precision precision) {
        return new SettleDecimal(super.multiply(multiplicand).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal multiply(SettleDecimal multiplicand) {
        return new SettleDecimal(super.multiply(multiplicand));
    }

    public SettleDecimal multiply(Integer multiplicand, Precision precision) {
        return new SettleDecimal(super.multiply(new BigDecimal(multiplicand.toString()).setScale(precision.getScale(), precision.getRoundingMode())));
    }

    public SettleDecimal multiply(Integer multiplicand) {
        return new SettleDecimal(super.multiply(new BigDecimal(multiplicand.toString())));
    }

    public SettleDecimal multiply(Double multiplicand, Precision precision) {
        return new SettleDecimal(super.multiply(new BigDecimal(multiplicand.toString())).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal multiply(Double multiplicand) {
        return new SettleDecimal(super.multiply(new BigDecimal(multiplicand.toString())));
    }


    // 减

    public SettleDecimal subtract(SettleDecimal subtrahend) {
        return new SettleDecimal(super.subtract(subtrahend));
    }

    public SettleDecimal subtract(SettleDecimal subtrahend, Precision precision) {
        return new SettleDecimal(super.subtract(subtrahend).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal subtract(Integer subtrahend) {
        return new SettleDecimal(super.subtract(new BigDecimal(subtrahend.toString())));
    }

    public SettleDecimal subtract(Integer subtrahend, Precision precision) {
        return new SettleDecimal(super.subtract(new BigDecimal(subtrahend.toString())).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal subtract(Double subtrahend) {
        return new SettleDecimal(super.subtract(new BigDecimal(subtrahend.toString())));
    }

    public SettleDecimal subtract(Double subtrahend, Precision precision) {
        return new SettleDecimal(super.subtract(new BigDecimal(subtrahend.toString())).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    // 加

    public SettleDecimal add(SettleDecimal augend) {
        return new SettleDecimal(super.add(augend));
    }

    public SettleDecimal add(SettleDecimal augend, Precision precision) {
        return new SettleDecimal(super.add(augend).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal add(Integer augend) {
        return new SettleDecimal(super.add(new BigDecimal(augend.toString())));
    }

    public SettleDecimal add(Integer augend, Precision precision) {
        return new SettleDecimal(super.add(new BigDecimal(augend.toString())).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal add(Double augend) {
        return new SettleDecimal(super.add(new BigDecimal(augend.toString())));
    }

    public SettleDecimal add(Double augend, Precision precision) {
        return new SettleDecimal(super.add(new BigDecimal(augend.toString())).setScale(precision.getScale(), precision.getRoundingMode()));
    }


    // 幂

    public SettleDecimal pow(SettleDecimal index, Precision precision) {
        return new SettleDecimal(super.pow(index.intValue()).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal pow(SettleDecimal index) {
        return new SettleDecimal(super.pow(index.intValue()));
    }

    public SettleDecimal pow(Integer index, Precision precision) {
        return new SettleDecimal(super.pow(index).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal pow(Integer index) {
        return new SettleDecimal(super.pow(index));
    }


    // 最大

    public SettleDecimal max(SettleDecimal index, Precision precision) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) >= 0) {
            return new SettleDecimal(this.setScale(precision.getScale(), precision.getRoundingMode()));
        } else {
            return new SettleDecimal(decimal.setScale(precision.getScale(), precision.getRoundingMode()));
        }
    }

    public SettleDecimal max(SettleDecimal index) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) >= 0) {
            return this;
        } else {
            return index;
        }
    }

    public SettleDecimal max(Integer index, Precision precision) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) >= 0) {
            return new SettleDecimal(this.setScale(precision.getScale(), precision.getRoundingMode()));
        } else {
            return new SettleDecimal(decimal.setScale(precision.getScale(), precision.getRoundingMode()));
        }
    }

    public SettleDecimal max(Integer index) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) >= 0) {
            return this;
        } else {
            return new SettleDecimal(decimal);
        }
    }


    // 最小

    public SettleDecimal min(SettleDecimal index, Precision precision) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) <= 0) {
            return new SettleDecimal(this.setScale(precision.getScale(), precision.getRoundingMode()));
        } else {
            return new SettleDecimal(decimal.setScale(precision.getScale(), precision.getRoundingMode()));
        }
    }

    public SettleDecimal min(SettleDecimal index) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) <= 0) {
            return this;
        } else {
            return index;
        }
    }

    public SettleDecimal min(Integer index, Precision precision) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) <= 0) {
            return new SettleDecimal(this.setScale(precision.getScale(), precision.getRoundingMode()));
        } else {
            return new SettleDecimal(decimal.setScale(precision.getScale(), precision.getRoundingMode()));
        }
    }

    public SettleDecimal min(Integer index) {
        BigDecimal decimal = new BigDecimal(index.toString());
        if (this.compareTo(decimal) <= 0) {
            return this;
        } else {
            return new SettleDecimal(decimal);
        }
    }


    public SettleDecimal precision(Precision precision) {
        return new SettleDecimal(this.setScale(precision.getScale(), precision.getRoundingMode()));
    }

}
