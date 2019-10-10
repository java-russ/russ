package com.ysd.exception;

import com.ysd.enums.ResultEnum;

/**
 * Created by SPZ
 *
 * @date 2019/8/4 18:47
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
