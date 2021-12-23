package com.sym.constant;

public enum StatusEnum {


    undone(0, "未完成"),
    done(1, "已完成" ),
    update(2, "待更新" ),
    abandon(3, "已废弃" );

    private final Integer type;
    private final String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    private StatusEnum(Integer type, String desc){
        this.desc = desc;
        this.type = type;
    }
    public static StatusEnum enumByType(Integer type) {
        StatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            StatusEnum value = var1[var3];
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

}
