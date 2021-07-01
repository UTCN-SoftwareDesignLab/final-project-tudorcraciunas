package com.example.hardwarestore.item;

import com.example.hardwarestore.email.EmailServiceImpl;
import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.example.hardwarestore.item.model.DTO.SellItemDTO;
import com.example.hardwarestore.item.model.Item;
import com.example.hardwarestore.sms.SMSModel;
import com.example.hardwarestore.sms.SMSService;
import com.example.hardwarestore.user.UserRepository;
import com.example.hardwarestore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final SimpMessageSendingOperations webSock;
    private final SMSService smsService;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<ItemDTO> findAllDto(){
        List<Item> items = itemRepository.findAll();

        return items.stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    private Item findById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }

    public ItemDTO findByIdDto(Long id){
        return itemMapper.toDto((findById(id)));
    }

    public ItemDTO create(ItemDTO item){
        return itemMapper.toDto(itemRepository.save(
                itemMapper.fromDto(item)
        ));
    }

    public List<ItemDTO> findOutOfStock(){
        return itemRepository.findAllByQuantity(0)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> findAllByType(String type){
        return itemRepository.findAllByType(type)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO edit(Long id, ItemDTO itemDTO){
        Item item = findById(id);

        if(!itemDTO.getName().isEmpty() && !itemDTO.getName().isBlank())
            item.setName(itemDTO.getName());

        if(!itemDTO.getDetails().isEmpty() && !itemDTO.getDetails().isBlank())
            item.setDetails(itemDTO.getDetails());

        if(itemDTO.getPrice() > 0)
            item.setPrice(itemDTO.getPrice());

        if(itemDTO.getQuantity() >= 0)
            item.setQuantity(itemDTO.getQuantity());

        return itemMapper.toDto(itemRepository.save(item));
    }

    public void sellEmail(Long id, List<SellItemDTO> toSell) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Congrats on buying all those cool items! Here is a list! \n");

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id not found:" + id));
        for (SellItemDTO sell : toSell) {
            Item item = itemRepository.findById(sell.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Item with id not found to sell" + sell.getId()));
            if (sell.getQuantity() <= item.getQuantity()) {

                stringBuilder.append("Item name: ").append(item.getName()).append("\n");
                stringBuilder.append("Details: ").append(item.getDetails()).append("\n");
                stringBuilder.append("Quantity: ").append(sell.getQuantity()).append("\n");
                stringBuilder.append("\n");
                ItemDTO updatedItem = ItemDTO.builder()
                        .quantity(item.getQuantity() - sell.getQuantity())
                        .price(item.getPrice())
                        .details(item.getDetails())
                        .name(item.getName())
                        .type(item.getType())
                        .build();
            }

        }
        emailService.sendSimpleMessage(user.getEmail(), "Purchase processed!", stringBuilder.toString());
    }

    public void sellItems(List<SellItemDTO> toSell){
        for (SellItemDTO sell: toSell) {
            Item item = itemRepository.findById(sell.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Item with id not found to sell" + sell.getId()));
            if(sell.getQuantity() <= item.getQuantity()){
                ItemDTO updatedItem = ItemDTO.builder()
                        .quantity(item.getQuantity() - sell.getQuantity())
                        .price(item.getPrice())
                        .details(item.getDetails())
                        .name(item.getName())
                        .type(item.getType())
                        .build();
                edit(sell.getId(), updatedItem);

                if(updatedItem.getQuantity() == 0){
                    SMSModel sms = SMSModel.builder()
                            .reciever("+40724358808")
                            .message(item.getName() + " was just sold out!")
                            .build();
                    //smsService.send(sms);
                    //Comentat din cauza ca pe netul de la camin nu am acces la Twilio si pușcă
                    webSock.convertAndSend("/items", item.getName() + " has just sold out! Better stock up again! :)");

                }
            }
            else{
                try {
                    throw new Exception("Trying to sell more than possible! Don't get yourself in an awkward situation...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




        }
    }

    public void delete(Long id){
        itemRepository.deleteById(id);
    }

}
