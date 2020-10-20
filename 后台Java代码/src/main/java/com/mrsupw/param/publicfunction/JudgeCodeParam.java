package com.mrsupw.param.publicfunction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeCodeParam {
    private String nowCode;
    private String targetCode;
}
