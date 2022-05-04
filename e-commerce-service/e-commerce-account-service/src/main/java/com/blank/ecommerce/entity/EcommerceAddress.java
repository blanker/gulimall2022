package com.blank.ecommerce.entity;

import com.blank.ecommerce.account.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_ecommerce_address")
@EntityListeners(AuditingEntityListener.class)
public class EcommerceAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="phone", nullable = false)
    private String phone;

    @Column(name="province", nullable = false)
    private String province;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="address_detail", nullable =false)
    private String addressDetail;

    @Column(name="create_time", nullable = false)
    @CreatedDate
    private Date createTime;

    @Column(name="update_time", nullable = false)
    @LastModifiedDate
    private Date updateTime;

    public static EcommerceAddress to(Long userId, AddressInfo.AddressItem addressItem) {
        final EcommerceAddress ecommerceAddress = new EcommerceAddress();
        ecommerceAddress.setUserId(userId);
        ecommerceAddress.setUsername(addressItem.getUsername());
        ecommerceAddress.setPhone(addressItem.getPhone());
        ecommerceAddress.setProvince(addressItem.getProvince());
        ecommerceAddress.setCity(addressItem.getCity());
        ecommerceAddress.setAddressDetail(addressItem.getAddressDetail());
        ecommerceAddress.setCreateTime(addressItem.getCreateTime());
        ecommerceAddress.setUpdateTime(addressItem.getUpdateTime());
        return ecommerceAddress;
    }

    public static AddressInfo.AddressItem toAddressItem(EcommerceAddress ecommerceAddress) {
        final AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setId(ecommerceAddress.getId());
        addressItem.setUsername(ecommerceAddress.getUsername());
        addressItem.setPhone(ecommerceAddress.getPhone());
        addressItem.setProvince(ecommerceAddress.getProvince());
        addressItem.setCity(ecommerceAddress.getCity());
        addressItem.setAddressDetail(ecommerceAddress.getAddressDetail());
        addressItem.setCreateTime(ecommerceAddress.getCreateTime());
        addressItem.setUpdateTime(ecommerceAddress.getUpdateTime());
        return addressItem;
    }
}
