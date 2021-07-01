package com.example.hardwarestore.payment;

import com.example.hardwarestore.payment.DTO.PaymentDTO;
import com.example.hardwarestore.payment.DTO.StripeResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.hardwarestore.UrlMapping.CART;
import static com.example.hardwarestore.UrlMapping.ORDER;

@RestController
@RequestMapping(CART)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<PaymentDTO> checkoutItemDtoList) throws StripeException {
        Session session = paymentService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<StripeResponse>(stripeResponse,HttpStatus.OK);
    }
}
