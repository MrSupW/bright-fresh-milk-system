package com.mrsupw.param.order.currentorder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeCodeValidationParam {
    private String nowCode;
    private String targetCode;
}
