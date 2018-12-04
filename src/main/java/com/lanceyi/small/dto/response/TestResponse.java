package com.lanceyi.small.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lance YI
 * @date 2018/12/4 16:58
 */
@Data
public class TestResponse {
    @ApiModelProperty("姓")
    private String firstName;

    @ApiModelProperty("名")
    private String lastName;

}
