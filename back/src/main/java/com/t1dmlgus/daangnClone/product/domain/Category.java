package com.t1dmlgus.daangnClone.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

    DIGITAL_DEVICE("디지털기기"),
    HOME_APPLIANCES("생활가전"),
    FURNITURE_INTERIOR("가구/인테리어"),
    CHILD("유아"),
    LIFE_PROCESSED_FOOD("생활/가공식품"),
    CHILDREN_BOOKS("유아도서"),
    WOMEN_CLOTHING("여성의류"),
    MEN_CLOTHING("남성의류"),
    GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티"),
    PET_SUPPLIES("반려동물용품"),
    BOOK_TICKET_ALBUM("도서/티켓/음반"),
    PLANT("식물"),
    OTHER_USED_GOODS("기타 중고물품"),
    USED_CAR("중고차");

    private final String krName;

}
