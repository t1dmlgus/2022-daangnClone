package com.t1dmlgus.daangnClone.likes.domain;

import com.t1dmlgus.daangnClone.BaseTimeEntity;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = {"product_id", "user_id"}
                )
        }
)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
