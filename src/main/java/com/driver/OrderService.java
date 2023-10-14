package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerID){
        orderRepository.addPartner(partnerID);
    }
    public void addOrderPartnerPair(String orderId,String partnerID){
        orderRepository.addOrderPartnerPair(orderId,partnerID);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderByID(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
       return  orderRepository.getPartnerByID(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnersID(partnerId);
    }
    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }
    public Integer getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int intTime = Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3));
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerID(intTime,partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerID(partnerId);
    }
    public void deletetPartnerById(String partnerId){
        orderRepository.deletePartnerByID(partnerId);
    }
    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderByID(orderId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerID(partnerId);
    }


}
