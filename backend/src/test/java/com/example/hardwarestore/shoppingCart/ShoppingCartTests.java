package com.example.hardwarestore.shoppingCart;

import com.example.hardwarestore.BaseControllerTest;
import com.example.hardwarestore.cart.CartItemRepository;
import com.example.hardwarestore.cart.model.CartItem;
import com.example.hardwarestore.item.ItemRepository;
import com.example.hardwarestore.item.model.Item;
import com.example.hardwarestore.user.UserRepository;
import com.example.hardwarestore.user.model.ERole;
import com.example.hardwarestore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ShoppingCartTests  {

    private CartItemRepository cartItemRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @BeforeEach
    protected void setUp(){
        itemRepository.save(
                Item.builder()
                .name("First Item")
                .details("first")
                .quantity(3)
                .price(12F)
                .type("hard")
                .build()
        );

        userRepository.save(User.builder()
        .email("trying")
        .username("to do this")
        .password("veryhard")
        .build());
    }

    @Test
    void createCart(){
        User user = userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("No user id"));
        Item item = itemRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("No item id"));



    }


}
