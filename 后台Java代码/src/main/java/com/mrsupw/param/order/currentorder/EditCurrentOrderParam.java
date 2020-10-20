package com.mrsupw.param.order.currentorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditCurrentOrderParam {
    private String id;
    private String code;
    private String description;
}
