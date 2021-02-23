package cn.jc.dining.service;

import cn.jc.dining.domain.Order;
import cn.jc.dining.domain.Page;
import cn.jc.dining.util.Result;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    Result getAllOrder(Page page,String start,String end) throws ParseException;

    Result getOrderById(String openid);

    Result getOrderDetail(String ordernum);

    void insertOrder(Order order);

    Result deleteOrder(String ordernum);

    Result statictics();

    Result finalFood(Map<String,Object> map);
}
