package com.luzm.amis.gen.bean;

import lombok.Data;

@Data
public class BaseAmisResponse<T> {
    /**
     * {
     * "status": 0,
     * "msg": "",
     * "data": {
     * // 正确
     * "text": "World!"
     * }
     * }
     */
    private Integer status = 0;
    private String msg = "success";
    private T data;
}