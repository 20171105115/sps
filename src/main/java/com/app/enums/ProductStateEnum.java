package com.app.enums;

public enum ProductStateEnum {

    SUCCESS(1,"成功"),INNER_ERROR(-1000,"内部错误"),EMPTY_PRODUCT(-1001,"要操作的商品为空")
    ,EMPTY_IMG(-1003,"缩略图为空");

    private int state;

    private String stateInfo;

    ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum stateOf(int index) {
        for (ProductStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }


    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
