package com.example.one.po;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author LuoBo
 * @Date 2022/1/26
 */
@Getter
@AllArgsConstructor
public enum OperationType {

    /**
     * 写数据
     */
    Write(0, "write"),
    /**
     * 删除数据
     */
    Delete(0, "delete");

    private Integer code;
    private String description;

    /**
     * 构造函数
     *
     * @author 许伟强51189
     * @date 2022/4/7 10:22
     * @param code        状态码
     * @param description 状态描述
     * @return
     */
    OperationType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OperationType getScriptStatusEnum(Integer code) {
        for (OperationType e : OperationType.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

}