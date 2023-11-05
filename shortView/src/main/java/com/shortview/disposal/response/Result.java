package com.shortview.disposal.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author wanghui
 * @Date 2023/11/3 0003 16:52
 * @Version 1.0
 */
@Data
public final class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private Result(){
    }
    public static <T> Result<T> ok(){
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage(HttpStatus.OK.getReasonPhrase());
        return result;
    }
    public static <T> Result<T> error(){
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.PRECONDITION_FAILED.value());
        result.setMessage(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
        return result;
    }
    public Result<T> code(int code){
        this.setCode(code);
        return this;
    }
    public Result<T> message(String message){
        this.message=message;
        return this;
    }
    public Result<T> data(T data){
        this.setData(data);
        return this;
    }
}

