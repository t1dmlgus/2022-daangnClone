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

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "price")
    private int price;

    @Column(name = "caption")
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Product(String title, int price, String caption,SaleStatus status, Category category, User user) {
        this.title = title;
        this.price = price;
        this.caption = caption;
        this.category = category;
        this.status = status;
        this.user = user;
    }
}
