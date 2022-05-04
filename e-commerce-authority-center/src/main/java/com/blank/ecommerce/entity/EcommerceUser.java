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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_ecommerce_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="extra_info", nullable = false)
    private String extraInfo;

    @Column(name="create_time", nullable = false)
    @CreatedDate
    private Date createTime;

    @Column(name="update_time", nullable = false)
    @LastModifiedDate
    private Date updateTime;
}
