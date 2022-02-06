package com.t1dmlgus.daangnClone.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 채팅방 조회(채팅방 번호)
    ChatRoom getByRoomId(String roomId);

    // 채팅방 조회(판매자, 구매자)
    ChatRoom findBySellerIdAndBuyerId(Long sellerId, Long buyerId);

    // 채팅방 모두 조회(판매자)
    List<ChatRoom> findAllBySellerId(Long sellerId);
}
