package com.mrsupw.param.product.puremilk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchProductsParam {
    String searchContent;
    int currentPageIndex;
    int pageSize;
}
