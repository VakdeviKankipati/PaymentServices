package com.vakya.paymentservice.controller;

import com.razorpay.RazorpayException;
import com.vakya.paymentservice.dto.InitiatePaymentRequestDto;
import com.vakya.paymentservice.service.strategy.PaymentGatewaySelectionStrategy;
import com.vakya.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    //@Autowired//field injection
   // private PaymentService paymentService;
    //constructor injection
    //public PaymentController(PaymentService paymentService){
     //   this.paymentService = paymentService;
    //}

    private PaymentService razorpayPaymentServices;
    private PaymentService stripePaymentServices;
    private PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy;

    public PaymentController(
            @Qualifier("razorpay") PaymentService razorpayPaymentServices,
            @Qualifier("stripe") PaymentService stripePaymentServices,
            PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy){
        this.razorpayPaymentServices = razorpayPaymentServices;
        this.stripePaymentServices = stripePaymentServices;
        this.paymentGatewaySelectionStrategy = paymentGatewaySelectionStrategy;
    }

    @PostMapping("/payment")
    public String intiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) throws RazorpayException {
        int paymentGatewayOption = choosePaymentGateway();
        switch (paymentGatewayOption){
            case 1 : return razorpayPaymentServices.doPayment(requestDto.getEmail(),
                    requestDto.getPhoneNumber(),
                    requestDto.getAmount(),
                    requestDto.getOrderId()
            );
            case 2 : return  stripePaymentServices.doPayment(requestDto.getEmail(),
                    requestDto.getPhoneNumber(),
                    requestDto.getAmount(),
                    requestDto.getOrderId()
            );
        }
       /* return  paymentService.doPayment(requestDto.getEmail(),
                requestDto.getPhoneNumber(),
                requestDto.getAmount(),
                requestDto.getOrderId()
                );*/
        return null;
    }
    private  int choosePaymentGateway(){
        return paymentGatewaySelectionStrategy.paymentGatewaySelection();
    }


}
