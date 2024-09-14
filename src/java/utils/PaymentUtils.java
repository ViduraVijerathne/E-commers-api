/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import dto.OrderItemDTO;
import dto.PaymentObject;
import entity.OrderEntity;
import entity.OrderItemEntity;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author vidur
 */
public class PaymentUtils {

    static double calculateTotal(List<OrderItemEntity> items) {
        double total = 0;
        for (OrderItemEntity i : items) {
            double price = i.getStocks().getProduct().getPrice();
            int qty = i.getQty();
            double shipping = i.getStocks().getProduct().getShipping();

            total += (price * qty) + (shipping * qty);

        }
        return total;
    }

    @Deprecated
    public static HashMap<String, Object> makePayhereObjectOld(OrderEntity order) {
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("sandbox", true);  // true if using Sandbox Merchant ID
        obj.put("merchant_id", "1228223");  // Replace with your Merchant ID
        obj.put("merchant_secret", "MTI3Mzg5NzMxNTc3MjM2Nzg5NjI4ODk0NTI2MDAyNzQ0MjAzNzU2");  // Replace with your Merchant Secret
        obj.put("notify_url", "http://sample.com/notify");
        obj.put("order_id", String.valueOf(order.getId()));
        obj.put("items", order.getOrderItems().get(0).getStocks().getProduct());
        obj.put("amount", calculateTotal(order.getOrderItems()));
        obj.put("currency", "LKR");
        obj.put("first_name", order.getUser().getFirstName());
        obj.put("last_name", order.getUser().getLastName());
        obj.put("email", order.getUser().getEmail());
        obj.put("phone", order.getAddressBook().getContact());
        obj.put("address", order.getAddressBook().getLine1() + " " + order.getAddressBook().getLine2());
        obj.put("city", order.getAddressBook().getCity());
        obj.put("country", "Sri Lanka");
        obj.put("delivery_address", order.getAddressBook().getLine1() + " " + order.getAddressBook().getLine2());
        obj.put("delivery_city", order.getAddressBook().getCity());
        obj.put("delivery_country", "Sri Lanka");
        obj.put("custom_1", "");
        obj.put("custom_2", "");

        PaymentObject o = new PaymentObject();
        o.setSandbox(true);

        return obj;
    }

    static public PaymentObject makePayhereObject(OrderEntity order) {
        // Initialize a new PaymentObject
        PaymentObject payment = new PaymentObject();

        // Set the values directly into the PaymentObject
        payment.setSandbox(true);  // true if using Sandbox Merchant ID
        payment.setMerchant_id("1228223");  // Replace with your Merchant ID
        payment.setMerchantSecret("MTI3Mzg5NzMxNTc3MjM2Nzg5NjI4ODk0NTI2MDAyNzQ0MjAzNzU2");  // Replace with your Merchant Secret
        payment.setNotify_url("http://sample.com/notify");

        // Order-related data
        payment.setOrder_id(String.valueOf(order.getId()));
        payment.setItems(order.getOrderItems().get(0).getStocks().getProduct().getName());  // Assuming product has a getName() method
        payment.setAmount(calculateTotal(order.getOrderItems()));
        payment.setCurrency("LKR");

        // User-related data
        payment.setFirst_name(order.getUser().getFirstName());
        payment.setLast_name(order.getUser().getLastName());
        payment.setEmail(order.getUser().getEmail());
        payment.setPhone(order.getAddressBook().getContact());

        // Address-related data
        String fullAddress = order.getAddressBook().getLine1() + " " + order.getAddressBook().getLine2();
        payment.setAddress(fullAddress);
        payment.setCity(order.getAddressBook().getCity());
        payment.setCountry("Sri Lanka");

        // Delivery-related data
        payment.setDeliveryAddress(fullAddress);
        payment.setDeliveryCity(order.getAddressBook().getCity());
        payment.setDeliveryCountry("Sri Lanka");

        // Custom fields
        payment.setCustom1("");
        payment.setCustom2("");

        // Return the populated PaymentObject
        return payment;
    }

}
