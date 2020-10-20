package com.mrsupw.param.currentorderdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditSaleItemParam {
    private String saleItemId;
    private Integer quantity;
}
