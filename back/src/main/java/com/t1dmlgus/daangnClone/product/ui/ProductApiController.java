package com.t1dmlgus.daangnClone.product.ui;

import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.product.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/product")
@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductApiController.class);

    @PostMapping()
    public ResponseEntity<?> register(ProductRequestDto productRequestDto, @RequestPart(value = "file") MultipartFile file) {   // 유저 추가(세션)

        logger.info("productRequestDto, {}", productRequestDto);
        logger.info("multipartFile-file, {}", file);

        Long userId = 1L;
        ResponseDto<?> registerDto = productService.registerProduct(productRequestDto, file);

        return new ResponseEntity<>(registerDto, HttpStatus.CREATED);

    }


}
