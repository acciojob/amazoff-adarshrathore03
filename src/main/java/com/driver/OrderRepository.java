package com.driver;
import org.springframework.stereotype.Repository;

import  java.util.*;
import java.util.HashMap;
@Repository
public class OrderRepository {
    HashMap<String,Order> orderHashMap = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
    HashMap<String,List<String>> listHashMap = new HashMap<>();
    HashMap<String,String> assignedOrderMap = new HashMap<>();

    public void addOrder(Order order){
        String orderId = order.getId();
        orderHashMap.put(orderId,order);
    }
    public void addPartner(String partnerID){

        deliveryPartnerHashMap.put(partnerID,new DeliveryPartner(partnerID));
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){
            List<String> orderList = listHashMap.getOrDefault(partnerId,new ArrayList<>());
            orderList.add(orderId);
            listHashMap.put(partnerId,orderList);
        }
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(deliveryPartnerHashMap.get(partnerId).getNumberOfOrders()+1);
        //order assigned
        assignedOrderMap.put(orderId,partnerId);
    }
    public Order getOrderByID(String orderID ){
        return  orderHashMap.get(orderID);
    }
    public DeliveryPartner getPartnerByID(String partnerID){
        return deliveryPartnerHashMap.get(partnerID);
    }
    public List<String> getOrdersByPartnersID(String partnerID){
        return listHashMap.get(partnerID);
    }
    public List<String> getAllOrders(){
        return new ArrayList<>(orderHashMap.keySet());
    }
    public int getCountOfUnassignedOrders(){
        return  orderHashMap.size()-assignedOrderMap.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerID(int time,String partnerID){
        int cnt = 0;
        List<String> ordersToCheck = listHashMap.get(partnerID);
        for(String orderId : ordersToCheck){
            if(orderHashMap.get(orderId).getDeliveryTime()>time){
                cnt++;
            }
        }
        return cnt;
    }
    public String getLastDeliveryTimeByPartnerID(String partnerId){
        List<String> orderList = listHashMap.getOrDefault(partnerId,new ArrayList<>());
        int last = Integer.MIN_VALUE;
        if(!orderList.isEmpty()){
            for(String order : orderList){
                int time = orderHashMap.get(order).getDeliveryTime();
                last = Math.max(time,last);
            }
        }
        int deliveryTimeInMinutes = last;
        int hours = deliveryTimeInMinutes/60;
        int minutes = deliveryTimeInMinutes%60;

        String HH = ""+hours;
        String MM = ""+minutes;

        if(HH.length()==1){
            HH='0'+HH;
        }
        if(MM.length()==1){
            MM='0'+MM;
        }
        return HH+':'+MM;
    }
    public void deletePartnerByID(String partnerID){
        List<String> orders = listHashMap.get(partnerID);
        for(String order : orders){
            assignedOrderMap.remove(order);
        }
        deliveryPartnerHashMap.remove(partnerID);
        listHashMap.remove(partnerID);
    }
    public void deleteOrderByID(String orderID){
        orderHashMap.remove(orderID);
        String partnerID = assignedOrderMap.get(orderID);
        listHashMap.get(partnerID).remove(orderID);
        deliveryPartnerHashMap.get(partnerID).setNumberOfOrders(deliveryPartnerHashMap.get(partnerID).getNumberOfOrders()-1);
        assignedOrderMap.remove(orderID);
    }
    public int getOrderCountByPartnerID(String partnerID){
        return deliveryPartnerHashMap.get(partnerID).getNumberOfOrders();
    }
}
