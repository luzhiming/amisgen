package com.luzm.amis.gen.bean;

import lombok.Data;

@Data
public class BaseAmisListResponse {
    /**
     * {
     * "status": 0,
     * "msg": "",
     * "data": {
     * "items": [
     * {
     * // 每一行的数据
     * "id": 1,
     * "xxx": "xxxx"
     * }
     * ],
     * <p>
     * "total": 200 // 注意！！！这里不是当前请求返回的 items 的长度，而是数据库中一共有多少条数据，用于生成分页组件
     * // 如果你不想要分页，把这个不返回就可以了。
     * }
     * }
     */
    private Integer status = 0;
    private String msg="success";
    private BaseAmisListData data;

}