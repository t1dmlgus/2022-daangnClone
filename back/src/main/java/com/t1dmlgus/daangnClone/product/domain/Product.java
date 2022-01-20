package com.t1dmlgus.daangnClone.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t1dmlgus.daangnClone.BaseTimeEntity;
import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    private String caption;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Builder
    public Product(String title, int price, String caption,SaleStatus status, User user) {
        this.title = title;
        this.price = price;
        this.caption = caption;
        this.status = status;
        this.user = user;
    }
}
