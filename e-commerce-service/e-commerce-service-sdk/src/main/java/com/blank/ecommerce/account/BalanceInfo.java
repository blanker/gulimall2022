package com.blank.ecommerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户账户余额信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceInfo {
    @Schema(description = "用户主键ID")
    private Long userId;
    @Schema(description = "用户账户余额")
    private Long balance;
}
