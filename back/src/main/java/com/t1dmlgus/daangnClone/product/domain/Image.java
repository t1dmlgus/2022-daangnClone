package com.t1dmlgus.daangnClone.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t1dmlgus.daangnClone.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "product")
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String fileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(String fileName, Product product) {
        this.fileName = fileName;
        this.product = product;
    }

}
