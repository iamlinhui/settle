package cn.promptness.settle.enums;

import lombok.Getter;

/**
 *
 * @author lynn
 * @date 2020/6/12 17:58
 * @since v1.0.0
 */
@Getter
public enum RepayType {

    /**
     *
     */
    UNREPAY(0, "待还"),

    NORMAL_REPAY(1, "正常还款"),

    PART_PREPAY(2, "部分提前还"),

    OVERDUE_REPAY(3, "逾期还款"),

    ALL_PREPAY(4, "全部提前还"),

    BAD_LOANS_COMPENSATION(5, "坏账代偿"),

    BUY_BACK(6, "回购");;

    /**
     * code
     */
    private final int code;
    /**
     * desc
     */
    private final String desc;

    RepayType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
