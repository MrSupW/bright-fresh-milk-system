package com.mrsupw.param.product.yogurt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditProductParam {
    private String id;

    private String code;

    private String description;

    private Float price;

    private Date productionDate;

    private String shelfLife;

    private String type;

    private String diluteConcentration;


}
