package com.mrsupw.param.storage.milkdrink;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditStorageParam {
    private String id;

    private String code;

    private String description;

    private Float price;

    private Date productionDate;

    private String shelfLife;

    private String flavor;

    private String sugar;

    private Integer quantity;
}
