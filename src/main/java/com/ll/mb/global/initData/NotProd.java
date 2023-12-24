package com.ll.mb.global.initData;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.book.book.service.BookService;
import com.ll.mb.domain.cash.cash.entity.CashLog;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.member.member.service.MemberService;
import com.ll.mb.domain.product.cart.service.CartService;
import com.ll.mb.domain.product.order.entity.Order;
import com.ll.mb.domain.product.order.service.OrderService;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration
@RequiredArgsConstructor
public class NotProd { // TDD를 고려해 테스트 전에 한번씩 실행되게 하기 위한 코드
    @Autowired
    @Lazy
    private NotProd self;
    private final MemberService memberService;
    private final BookService bookService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @Bean
    ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if ( memberService.findByUsername("admin").isPresent()) return;

        Member memberAdmin = memberService.join("user1", "1234").getData();
        Member memberUser1 = memberService.join("user2", "1234").getData();
        Member memberUser2 = memberService.join("user3", "1234").getData();
        Member memberUser3 = memberService.join("admin", "1234").getData();

        Book book1 = bookService.createBook(memberUser1 ,"책 제목 1", "책 내용 1", 10_000);
        Book book2 = bookService.createBook(memberUser2 ,"책 제목 2", "책 내용 2", 20_000);
        Book book3 = bookService.createBook(memberUser2 ,"책 제목 3", "책 내용 3", 30_000);
        Book book4 = bookService.createBook(memberUser3 ,"책 제목 4", "책 내용 4", 40_000);
        Book book5 = bookService.createBook(memberUser3 ,"책 제목 5", "책 내용 5", 15_000);
        Book book6 = bookService.createBook(memberUser3 ,"책 제목 6", "책 내용 6", 20_000);

        Product product1 = productService.createProduct(book3);
        Product product2 = productService.createProduct(book4);
        Product product3 = productService.createProduct(book5);
        Product product4 = productService.createProduct(book5);

        cartService.addItem(memberUser1, product1);
        cartService.addItem(memberUser1, product2);
        cartService.addItem(memberUser1, product3);

        // rel = related
        memberService.addCash(memberUser1, 150_000, CashLog.EventType.충전__무통장입금, memberUser1);
        memberService.addCash(memberUser1, -20_000, CashLog.EventType.출금__통장입금, memberUser1);

        Order order1 = orderService.createFromCart(memberUser1);

        long order1PayPrice = order1.calcPayPrice();

        orderService.payByCashOnly(order1);
    }
}
