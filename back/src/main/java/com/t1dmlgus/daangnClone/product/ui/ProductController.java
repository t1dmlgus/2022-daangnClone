package com.t1dmlgus.daangnClone.product.ui;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String randing(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDto<?> allProductDtos = productService.allProduct(principalDetails.getUser().getId());

        logger.info("product.getData() {}", allProductDtos.getData());
        model.addAttribute("product", allProductDtos.getData());
        model.addAttribute("user", principalDetails.getUser());

        return "product/randing";
    }

    // 상품 상세 페이지
    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){

        ResponseDto<?> productDetails = productService.inquiryProduct(productId, principalDetails.getUser().getId());
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
