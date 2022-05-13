package com.blank.ecommerce.entity;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_ecommerce_logistics")
public class EcommerceLogistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    private Long userId;
    private Long orderId;
    private Long addressId;
    private String extraInfo;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    public EcommerceLogistics(Long userId, Long orderId, Long addressId, String extraInfo) {
        this.userId = userId;
        this.orderId = orderId;
        this.addressId = addressId;
        this.extraInfo = StrUtil.isEmpty(extraInfo) ? "{}": extraInfo;
    }
}
