package com.mrsupw.param.product.jelly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsParam {
    int pageSize ;
    int currentPageIndex;
}
