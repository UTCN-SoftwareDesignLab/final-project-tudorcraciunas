package com.example.hardwarestore;

import com.example.hardwarestore.cart.CartItemRepository;
import com.example.hardwarestore.email.EmailServiceImpl;
import com.example.hardwarestore.item.ItemRepository;
import com.example.hardwarestore.item.model.Item;
import com.example.hardwarestore.security.AuthService;
import com.example.hardwarestore.security.dto.SignupRequest;
import com.example.hardwarestore.sms.SMSModel;
import com.example.hardwarestore.sms.SMSService;
import com.example.hardwarestore.user.RoleRepository;
import com.example.hardwarestore.user.UserRepository;
import com.example.hardwarestore.user.model.ERole;
import com.example.hardwarestore.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
//TODO: adaptat bootstrapper
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CartItemRepository cartItemRepository;

    private final AuthService authService;

    private final SMSService smsService;
    private final EmailServiceImpl emailService;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {

            cartItemRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            itemRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("tudor@gmail.com")
                    .username("tudor")
                    .password("tudor")
                    .phone("+40724358808")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("user@gmail.com")
                    .username("user2")
                    .password("user2")
                    .phone("+40724358808")
                    .roles(Set.of("REGULAR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("tudor.craciunas@gmail.com")
                    .username("user1")
                    .password("user1")
                    .phone("+40724358808")
                    .roles(Set.of("REGULAR"))
                    .build());

            itemRepository.save(Item.builder()
            .name("Cool keyboard")
            .details("RGB Lighting, usb cable, English type keyboard")
            .price(122.89F)
            .quantity(7)
            .type("Hard")
            .build()
                    );
            itemRepository.save(Item.builder()
                    .name("Cool mouse")
                    .details("RGB Lighting, wireless, includes battery")
                    .price(72.89F)
                    .quantity(3)
                    .type("Hard")
                    .build()
            );

            itemRepository.save(Item.builder()
                    .name("Cool monitor")
                    .details("Full HD, 16``, comes with wife")
                    .price(222.89F)
                    .quantity(51)
                    .type("Very cool bro")
                    .build()
            );

            SMSModel sms = SMSModel.builder()
                    .reciever("+40724358808")
                    .message("CONGRATS, IT'S WORKING!")
                    .build();
//            smsService.send(sms);
//Comentat din cauza ca pe netul de la camin nu am acces la Twilio si pușcă
           emailService.sendSimpleMessage("tudor.craciunas@gmail.com","smoritu", "Congrats");






        }
    }
}
