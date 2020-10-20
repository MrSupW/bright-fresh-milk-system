package com.mrsupw.param.product.yogurt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductParam {
    private String id;
    private String code;
}
