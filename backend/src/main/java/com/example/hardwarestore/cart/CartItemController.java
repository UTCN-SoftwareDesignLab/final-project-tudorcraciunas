package com.example.hardwarestore.cart;

import com.example.hardwarestore.cart.model.CartItem;
import com.example.hardwarestore.cart.model.DTO.CartItemDto;
import com.example.hardwarestore.item.ItemService;
import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.example.hardwarestore.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.hardwarestore.UrlMapping.*;

@RestController
@RequestMapping(CART)
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final ItemService itemService;

    @GetMapping
    public List<ItemDTO> showShoppingCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        //TODO: asta si-o dat null Exception
        System.out.println(userDetails.getId());
        List<CartItemDto> cartItems = cartItemService.findByUserId(userDetails.getId());

        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (CartItemDto cartItem : cartItems
             ) {
            System.out.println(cartItem.toString());
            ItemDTO itemDTO = itemService.findByIdDto(cartItem.getItem_id());
            itemDTO.setQuantity(cartItem.getQuantity());
            itemDTOS.add(itemDTO);
        }

        System.out.println(itemDTOS.toString());
        return itemDTOS;
    }

    @GetMapping(ENTITY)
    public List<ItemDTO> showShoppingCart(@PathVariable Long id){
        System.out.println("id try " + id);
        List<CartItemDto> cartItems = cartItemService.findByUserId(id);

        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (CartItemDto cartItem : cartItems
        ) {
            System.out.println(cartItem.toString());
            ItemDTO itemDTO = itemService.findByIdDto(cartItem.getItem_id());
            itemDTO.setQuantity(cartItem.getQuantity());
            itemDTOS.add(itemDTO);
        }

        System.out.println(itemDTOS);
        return itemDTOS;
    }

    @PostMapping
    public CartItemDto addToCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartItemDto cartItemDto){
        return cartItemService.save(CartItemDto.builder()
        .item_id(cartItemDto.getItem_id())
        .user_id(userDetails.getId())
        .quantity(cartItemDto.getQuantity())
        .build());
    }



    @DeleteMapping(ENTITY + USER)
    public void delete(@PathVariable Long id, @PathVariable Long userId){
        cartItemService.deleteByItemIdAndUserId(id, userId);
    }

    @DeleteMapping(USER)
    public void deleteAll(@PathVariable Long userId){

        cartItemService.deleteByUserId(userId);
    }

    @PatchMapping(ENTITY)
        public CartItemDto edit(@RequestBody CartItemDto cartItemDto, @PathVariable Long id){
        return cartItemService.update(id, cartItemDto);
    }



}
