package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    // 상품 등록
    ResponseDto<?> registerProduct(ProductRequestDto productRequestDto, MultipartFile multipartFile, User user);

    // 상품 조회
    ResponseDto<?> inquiryProduct(Long productId);

    // 상품 수정

    // 상품 삭제


}
