package com.mrsupw.param.product.milkdrink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewProductParam {
    private String code;
    private String description;
    private float price;
    private Date productionDate;
    private String shelfLife;
    private String flavor;
    private String sugar;
}
