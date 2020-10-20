package com.mrsupw.param.storage.yogurt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewStorageParam {
    private String code;
    private String description;
    private float price;
    private Date productionDate;
    private String shelfLife;
    private String diluteConcentration;
    private String type;
    private Integer quantity;
}
