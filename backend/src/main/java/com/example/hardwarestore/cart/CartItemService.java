package com.example.hardwarestore.cart;

import com.example.hardwarestore.cart.mapper.CartItemMapper;
import com.example.hardwarestore.cart.model.CartItem;
import com.example.hardwarestore.cart.model.DTO.CartItemDto;
import com.example.hardwarestore.item.ItemRepository;
import com.example.hardwarestore.item.model.Item;
import com.example.hardwarestore.user.UserRepository;
import com.example.hardwarestore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public List<CartItemDto> findAllDto(){
        List<CartItem> cartItems = cartItemRepository.findAll();

        return cartItems.stream()
                .map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    public CartItemDto findById(Long id){
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Cart item with id not found" + id));
    }

    public void deleteByItemIdAndUserId(Long itemId, Long userId){

       Long id =  cartItemRepository.findCartItemByUserIdAndItemId(userId, itemId).getId();
        System.out.println(id);
       cartItemRepository.deleteById(id);
    }

    public void deleteById(Long id){
        cartItemRepository.deleteById(id);
    }
    public void deleteByUserId(Long userId){

        List<CartItemDto> cartItems = findByUserId(userId);

        for (CartItemDto c : cartItems) {
            deleteById(c.getId());
        }

        cartItemRepository.deleteAllByUserId(userId);
    }

    public CartItemDto save(CartItemDto cartItemDto){
        User client = userRepository.findById(cartItemDto.getUser_id()).orElseThrow( () -> new EntityNotFoundException("User not found with id: " + cartItemDto.getUser_id()));
        Item item = itemRepository.findById(cartItemDto.getItem_id()).orElseThrow( () -> new EntityNotFoundException("Item not found with id: " + cartItemDto.getItem_id()));

        CartItem cartItem = CartItem.builder()
                .item(item)
                .user(client)
                .quantity(cartItemDto.getQuantity())
                .build();

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    public List<CartItemDto> findByUserId(Long userId){
        List<CartItem> userItems = cartItemRepository.findCartItemByUserId(userId);

        return userItems.stream()
                .map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    public CartItemDto update(Long id, CartItemDto cartItemDto){

        CartItem cartItem = CartItem.builder()
                .item(itemRepository.findById(cartItemDto.getItem_id()).orElseThrow(() -> new EntityNotFoundException()))
                .user(userRepository.findById(cartItemDto.getUser_id()).orElseThrow(() -> new EntityNotFoundException()))
                .quantity(cartItemDto.getQuantity())
                .build();

        cartItem.setId(cartItemRepository.findCartItemByUserIdAndItemId(cartItemDto.getUser_id(), cartItemDto.getItem_id()).getId());

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }
}
