package com.mrsupw.param.currentorderdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddNewSaleItemParam {
    private String orderId;
    private String code;
    private Integer quantity;
}
