package com.t1dmlgus.daangnClone.likes.ui.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductLikesStatus {

    private boolean likesStatus;
    private int likesCount;

    public ProductLikesStatus(boolean statusUserLikesProduct, int countProductLikes) {
        this.likesStatus = statusUserLikesProduct;
        this.likesCount = countProductLikes;
    }
}
