package com.example.hardwarestore.cart;

import com.example.hardwarestore.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findCartItemByUserId(Long userId);
    CartItem findCartItemByUserIdAndItemId(Long userId, Long itemId);
    void deleteCartItemByItemIdAndUserId(Long itemId, Long userId);
    void deleteAllByUserId(Long userId);
}
