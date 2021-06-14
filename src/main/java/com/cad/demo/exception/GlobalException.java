package com.cad.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private Integer code;
    private String msg;
    public GlobalException(int i, String 用户名已存在) {

    }

//    public GlobalException(Integer code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

}
