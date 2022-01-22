package com.t1dmlgus.daangnClone.product.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/product")
@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductApiController.class);

    // 상품 등록
    @PostMapping("/register")
    public ResponseEntity<?> register(ProductRequestDto productRequestDto, @RequestPart(value = "file") MultipartFile file, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        logger.info("productRequestDto222, {}", productRequestDto);

        ResponseDto<?> registerDto = productService.registerProduct(productRequestDto, file, principalDetails.getUser());
        logger.info(SecurityContextHolder.getContext().toString());

        return new ResponseEntity<>(registerDto, HttpStatus.CREATED);
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> inquiryProduct(@PathVariable Long productId) {

        ResponseDto<?> productDetailDto = productService.inquiryProduct(productId);

        return new ResponseEntity<>(productDetailDto, HttpStatus.OK);

    }


    
    
}
