package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.product.ProductRequestDto;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    // 상품 등록
    ResponseDto<?> register(ProductRequestDto productRequestDto, MultipartFile multipartFile, Long userId);
}
