package com.t1dmlgus.daangnClone.product.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

        ResponseDto<?> registerDto = productService.registerProduct(productRequestDto, file, principalDetails.getUser());
        logger.info(SecurityContextHolder.getContext().toString());
        return new ResponseEntity<>(registerDto, HttpStatus.CREATED);
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> inquiryProduct(@PathVariable Long productId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDto<?> productDetailDto = productService.inquiryProduct(productId, principalDetails.getUser().getId());
        return new ResponseEntity<>(productDetailDto, HttpStatus.OK);

    }

    // 상품 전체 조회, 페이징 적용
    @GetMapping("/randing")
    public ResponseEntity<?> allProduct(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        ResponseDto<?> allProductDtos = productService.allProduct(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(allProductDtos, HttpStatus.OK);
    }

    // 카테고리 조회, 페이징 적용
    @GetMapping("/category/{category}")
    public ResponseEntity<?> allProduct(@PathVariable Category category, @AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        ResponseDto<?> product = productService.categoryProduct(category,principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    
    
}
