package com.lp.test.mybatisplusdemo.modules.test.constant;

/**是否删除常量
 * @author lp
 * @since 2020-12-12 16:12:39
 */
public enum IsDelete {
    NO("use", "0"),
    YES("delete", "1");

    private final String info;
    private final String code;

    IsDelete(String info, String code) {
        this.info = info;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
