package com.t1dmlgus.daangnClone.product.ui;

import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    // 랜딩 페이지
    @GetMapping("/randing")
    public String randing(Model model) {

        ResponseDto<?> product = productService.allProduct();

        logger.info("product.getData() {}", product.getData());

        model.addAttribute("product", product.getData());

        return "product/randing";
    }

    // 상품 상세 페이지
    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model){

        ResponseDto<?> productDetails = productService.inquiryProduct(productId);
        logger.info("productDetails {}", productDetails);
        model.addAttribute("product", productDetails.getData());

        return "product/product";
    }

    // 상품 모두보기 페이지
    @GetMapping("/all")
    public String allProduct(){
        return "product/all";
    }


    // 상품 등록
    @GetMapping("/register")
    public String registerProduct(){
        return "product/register";
    }

}
