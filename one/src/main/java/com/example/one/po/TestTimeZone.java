package com.example.one.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TestTimeZone {

    // 默认时区是UTC所以，默认就是: @JsonFormat(timezone = "GMT+0", pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date birthday;
}