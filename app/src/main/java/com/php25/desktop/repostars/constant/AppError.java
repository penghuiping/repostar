package com.php25.desktop.repostars.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * 错误表
 *
 * @author penghuiping
 * @date 2020/9/27 11:03
 */
public enum AppError implements BusinessErrorStatus {
    /**
     * 登入失败
     */
    LOGIN_ERROR("1000", "登入失败"),
    /**
     * 无法删除此组，组内含有元素
     */
    GROUP_NOT_EMPTY_ERROR("1001", "无法删除此组，组内含有元素");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String desc;

    AppError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
