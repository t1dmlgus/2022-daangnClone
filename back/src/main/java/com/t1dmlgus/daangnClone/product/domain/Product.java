package com.t1dmlgus.daangnClone.product.domain;

import com.t1dmlgus.daangnClone.BaseTimeEntity;
import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "Product_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    private String caption;

    // 거래 상태 -> enum
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Builder
    public Product(String title, int price, String caption, User user) {
        this.title = title;
        this.price = price;
        this.caption = caption;
        this.user = user;
    }
}
