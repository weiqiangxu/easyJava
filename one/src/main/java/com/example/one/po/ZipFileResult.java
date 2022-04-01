package com.example.one.po;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZipFileResult {
    private  String fileName;
    private byte[] contentByte;
    private String fileType;
}
