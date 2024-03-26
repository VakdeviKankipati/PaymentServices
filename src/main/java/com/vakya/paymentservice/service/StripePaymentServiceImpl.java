package com.vakya.paymentservice.service;

import com.razorpay.RazorpayException;
import com.vakya.paymentservice.dto.PaymentResponse;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentServiceImpl implements PaymentService {
    @Override
    public String doPayment(String email, String phone, Long amount, String orderId) throws RazorpayException {
        return null;
    }
}
