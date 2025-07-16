package com.dailycodework.buynowdotcom.service.Order;

import com.dailycodework.buynowdotcom.dtos.OrderDto;
import com.dailycodework.buynowdotcom.model.Order;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long userId);
    List<OrderDto> getUserOrders(Long userId);
    

}
