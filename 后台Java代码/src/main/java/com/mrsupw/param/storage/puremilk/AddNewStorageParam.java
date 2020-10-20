package com.mrsupw.param.storage.puremilk;

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
    private String countryOfOrigin;
    private String butterfat;
    private String protein;
    private Integer quantity;
}
