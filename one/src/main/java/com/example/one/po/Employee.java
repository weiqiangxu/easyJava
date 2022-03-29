package com.example.one.po;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class Employee {

    @NotNull(message = "name不能为空呀!")
    @Length(min = 3,max = 20, message = "name长度应该在3和20之间!")
    // @NotEmpty
    // @Range
    private String name;
}
