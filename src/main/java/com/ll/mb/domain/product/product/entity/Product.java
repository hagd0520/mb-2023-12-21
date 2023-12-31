package com.ll.mb.domain.product.product.entity;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.global.app.AppConfig;
import com.ll.mb.global.jpa.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Builder
@ToString(callSuper = true)
public class Product extends BaseTime {
    @ManyToOne
    private Member maker;
    private String relTypeCode;
    private long relId;
    private String name;
    private int price;
    private boolean published;

    public Book getBook() {
        return AppConfig.getEntityManager().getReference(Book.class, relId);
    }
}
