package com.t1dmlgus.daangnClone.chat.domain;

import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"buyer_Id", "seller_Id"})})
@ToString(exclude = {"seller", "buyer"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_Id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_Id")
    private User buyer;


    public ChatRoom(User seller, User buyer) {
        this.roomId = "CR_"+UUID.randomUUID();
        this.seller = seller;
        this.buyer = buyer;
    }
}