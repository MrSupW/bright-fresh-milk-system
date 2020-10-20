package com.mrsupw.param.storage.yogurt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStorageParam {
    int pageSize ;
    int currentPageIndex;
}
