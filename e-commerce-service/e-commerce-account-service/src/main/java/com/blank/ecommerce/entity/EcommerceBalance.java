package com.blank.ecommerce.entity;

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
@Table(name="t_ecommerce_balance")
@EntityListeners(AuditingEntityListener.class)
public class EcommerceBalance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="balance", nullable = false)
    private Long balance;

    @Column(name="create_time", nullable = false)
    @CreatedDate
    private Date createTime;

    @Column(name="update_time", nullable = false)
    @LastModifiedDate
    private Date updateTime;
}
