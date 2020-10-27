package com.iflytek.fpva.overseasapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 海外同传查询入参
 * Feng, Ge 2020-10-14 13:43
 */
@Data
public class QueryRequest {

    @ApiModelProperty(value = "搜索关键词")
    private String keywords;

    @ApiModelProperty(value = "国别：国内/国外")
    private String country_type;

    @ApiModelProperty(value = "当前页号")
    private String page;

    @ApiModelProperty(value = "页大小")
    private String page_size;

}
