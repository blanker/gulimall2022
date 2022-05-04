package com.blank.ecommerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户地址信息")
public class UserAddress {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "电话号码")
    private String phone;
    @Schema(description = "省")
    private String province;
    @Schema(description = "市")
    private String city;
    @Schema(description = "详细地址信息")
    private String addressDetail;

}
