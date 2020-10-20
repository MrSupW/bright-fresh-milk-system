package com.mrsupw.param.currentorderdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteSaleItemParam {
    private String orderId;
    private String saleItemId;
}
