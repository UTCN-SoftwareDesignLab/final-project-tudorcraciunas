package com.example.hardwarestore.cart.mapper;

import com.example.hardwarestore.cart.model.CartItem;
import com.example.hardwarestore.cart.model.DTO.CartItemDto;
import com.example.hardwarestore.item.ItemRepository;
import com.example.hardwarestore.item.model.Item;
import com.example.hardwarestore.user.UserRepository;
import com.example.hardwarestore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public interface CartItemMapper {



    @Mappings({
            @Mapping(target = "user_id", source = "user.id"),
            @Mapping(target = "item_id", source = "item.id")
    })
    CartItemDto toDto(CartItem cartItem);

//    @Mappings({
//            @Mapping(target = "user", expression = "java(findUser(user.id)"),
//            @Mapping(target = "item", source = "java(findItem(item.id)")
//    })
    CartItem fromDto(CartItemDto cartItemDto);


//    default User findUser(Long id){
//        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found" + id));
//    }
//    default Item findItem(Long id){
//        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found" + id));
//    }
}
