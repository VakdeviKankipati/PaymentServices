package com.vakya.paymentservice.service;

import com.razorpay.RazorpayException;
import com.vakya.paymentservice.dto.PaymentResponse;

public interface PaymentService {

    String doPayment(String email,String phone,Long amount, String orderId ) throws RazorpayException;

}
