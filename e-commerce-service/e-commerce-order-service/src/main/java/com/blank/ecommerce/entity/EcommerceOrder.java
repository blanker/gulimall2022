package com.blank.ecommerce.entity;

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
@Table(name="t_ecommerce_order")
@EntityListeners(AuditingEntityListener.class)
public class EcommerceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="address_id", nullable = false)
    private Long addressId;

    @Column(name="order_detail", nullable = false)
    private String orderDetail;

    @CreatedDate
    @Column(name="create_time", nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name="update_time", nullable = false)
    private Date updateTime;

    public EcommerceOrder(Long userId, Long addressId, String orderDetail) {
        this.userId = userId;
        this.addressId = addressId;
        this.orderDetail = orderDetail;
    }
}
