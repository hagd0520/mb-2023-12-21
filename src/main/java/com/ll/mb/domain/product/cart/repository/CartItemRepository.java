package com.ll.mb.domain.product.cart.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.cart.entity.CartItem;
import com.ll.mb.domain.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuyer(Member buyer);

    void deleteByBuyerAndProduct(Member buyer, Product product);

    boolean existsByBuyerAndProduct(Member buyer, Product product);
}
