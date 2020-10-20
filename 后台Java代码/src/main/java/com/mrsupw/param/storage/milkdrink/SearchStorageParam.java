package com.mrsupw.param.storage.milkdrink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchStorageParam {
    String searchContent;
    int currentPageIndex;
    int pageSize;
}
