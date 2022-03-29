package com.example.one.po;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Company {
    @NotNull(message = "公司名称不能为空")
    private String companyName;
}
