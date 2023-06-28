package com.read.read_book.common;

public enum ValidationEnum {
    register(1),Forget(2);

    ValidationEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private Integer code;

}
