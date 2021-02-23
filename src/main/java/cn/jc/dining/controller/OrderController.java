package cn.jc.dining.controller;

import cn.jc.dining.domain.Order;
import cn.jc.dining.domain.Page;
import cn.jc.dining.mapper.OrderMapper;
import cn.jc.dining.service.OrderService;
import cn.jc.dining.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;


    @RequestMapping("/order-list")
    public String orderlist(){
        return "order-list";
    }

    @RequestMapping("/order-add")
    public String orderadd(){
        return "order-add";
    }

    @RequestMapping("/order-statistics")
    public String orderstatistics(){
        return "order-statistics";
    }



    //      获得所有订单
    @GetMapping("/order")
    @ResponseBody
    public Result getAllOrder(Page page,String start,String end) throws ParseException {
        return orderService.getAllOrder(page,start,end);
    }

    //      获得所有订单
    @GetMapping("/count")
    @ResponseBody
    public String count(){
        return orderMapper.getAllCount();
    }

    //获得个人订单
    @GetMapping("/order/{openid}")
    @ResponseBody
    public Result getOrderById(@PathVariable("openid") String openid){
        return orderService.getOrderById(openid);
    }

//    根据订单号获得订单明细
    @GetMapping("/getOrderDetail/{ordernum}")
    @ResponseBody
    public Result getOrderDetail(@PathVariable("ordernum") String ordernum){
        return orderService.getOrderDetail(ordernum);
    }

    @PostMapping("/order")
    @ResponseBody
    public void insertOrder(Order order){
        orderService.insertOrder(order);
    }

    @PutMapping("/order")
    public Order updateOrder( @RequestBody Map m){
        return null;
    }


//删除订单
    @DeleteMapping("/order/{ordernum}")
    @ResponseBody
    public Result deleteOrder(@PathVariable("ordernum") String ordernum){
        return orderService.deleteOrder(ordernum);
    }
//查询实时订单数据
    @RequestMapping("/statictics")
    @ResponseBody
    public Result statictics(){
        return orderService.statictics();
    }

//    修改订单明细状态
    @RequestMapping("/finalFood")
    @ResponseBody
    @Transactional
    public Result statictics(@RequestParam  Map<String,Object> map){
        return orderService.finalFood(map);
    }
}
