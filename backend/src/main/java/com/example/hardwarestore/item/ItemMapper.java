package com.example.hardwarestore.item;


import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.example.hardwarestore.item.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDTO toDto(Item item);

    Item fromDto(ItemDTO item);

}
