package com.t1dmlgus.daangnClone.product.domain;

import com.t1dmlgus.daangnClone.BaseTimeEntity;
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


    @Builder
    public Product(String title, int price, String caption) {
        this.title = title;
        this.price = price;
        this.caption = caption;
    }
}
