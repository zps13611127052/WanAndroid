package com.example.codelibrary;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class ApiException extends Throwable {
    private String message;
    private int code;

    public ApiException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
