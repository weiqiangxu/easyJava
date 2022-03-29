package com.example.one.po;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@ApiMode - 标记为swagger的解析类
@Data
public class Work {

    // @ApiModelProperty - model属性的说明或者数据操作更改
    @NotNull(message = "company信息不能为空")
    private Company company;

    @NotEmpty(message = "location信息不能为空")
    private String location;
}