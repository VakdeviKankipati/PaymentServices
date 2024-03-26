package com.vakya.paymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vakya.paymentservice.dto.PaymentResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("razorpay")
public class RazorpayPaymentServiceImpl implements  PaymentService{
    private RazorpayClient razopayClient;

    public  RazorpayPaymentServiceImpl(RazorpayClient razorpayClient){
        this.razopayClient = razorpayClient;
    }
    @Override
    public String doPayment(String email, String phone, Long amount, String orderId) throws RazorpayException{
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("receipt", orderId);

            JSONObject customer = new JSONObject();
            customer.put("email", email);
            customer.put("phone", phone);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);

            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("callback_url", "http://domain.com/razorpayWebHook");
            paymentLinkRequest.put("callback_method", "post");
            PaymentLink response = razopayClient.paymentLink.create(paymentLinkRequest);
            // PaymentLink response = razopayClient.paymentLink.create(paymentLinkRequest);
            // orderRequest.put("notes",notes);

            return response.toString();
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }
}
