package com.blank.ecommerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户地址信息")
public class AddressInfo {

    @Schema(description = "地址信息所属用户ID")
    private Long userId;

    @Schema(description = "用户的地址列表")
    private List<AddressItem> addressItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "用户单个地址信息")
    public static class AddressItem {
        @Schema(description = "地址id")
        private Long id;
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
        @Schema(description = "创建时间")
        private Date createTime;
        @Schema(description = "更新时间")
        private Date updateTime;

        public AddressItem(Long id) {
            this.id = id;
        }

        public UserAddress toUserAddress(){
            return new UserAddress(username, phone, province, city, addressDetail);
        }
    }
}
