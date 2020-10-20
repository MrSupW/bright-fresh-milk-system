package com.mrsupw.param.order.currentorder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetCurrentOrdersParam {
    private Integer pageSize;
    private Integer currentPageIndex;
}
