package com.example.hardwarestore.item;

import com.example.hardwarestore.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByQuantity(Integer quantity);
    List<Item> findAllByType(String type);
}
