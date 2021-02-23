package cn.jc.dining.service.impl;

import cn.jc.dining.domain.Order;
import cn.jc.dining.domain.Page;
import cn.jc.dining.mapper.OrderMapper;
import cn.jc.dining.service.OrderService;
import cn.jc.dining.util.Result;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result getAllOrder(Page page,String start,String end) throws ParseException {
        Result result = new Result();
        PageHelper.startPage(page.getPage(),page.getRows());
        if (start != "" ){
            start += " 00:00:00";
            end += " 23:59:59";
        }
        System.out.println(start+"==="+end);
        List<Order> list = orderMapper.getAllOrder(start, end);
        result.setMsg(orderMapper.getAllCount());
        result.setData(list);
        result.setSuccess(true);
        return result;
    }



    @Override
    public Result getOrderById(String openid) {
        Result result = new Result();
        try {
            result.setData(orderMapper.getOrderById(openid));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result getOrderDetail(String ordernum) {
        Result result = new Result();
        try {
            result.setData(orderMapper.getOrderDetail(ordernum));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public Result deleteOrder(String ordernum) {

        int i = orderMapper.deleteOrder(ordernum);
        Result result = new Result();
        if(i>0){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 实时获得需要制作的菜单 直接按照时间将目前所有需要制作的菜查出来
     * */
    @Override
    public Result statictics() {
        Result result = new Result();
        List<Map<String,Object>> single = new ArrayList<>();
        List<Map<String,Object>> more = new ArrayList<>();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        List<Map<String,Object>> all = orderMapper.getAllOrderDetail(now);
        all.stream().forEach(i->{
            int issingle = new Double(Double.parseDouble(i.get("count").toString())).intValue();
            if(issingle == 1){//单份菜
                single.add(i);
            }else {//多份菜
                more.add(i);
            }
        });
        Map<String, List> map = new HashMap<>();
        map.put("single", single);
        map.put("more",more);
        result.setData(map);
        return result;
    }

    @Override
    public Result finalFood(Map<String,Object> map) {
        Result result = new Result();
        int i = orderMapper.finalFood(map.get("foodid").toString());
        if (i!=0){
//            在菜品确认出餐之后，对对应的库存进行删减
            subStock(map.get("count").toString(),map.get("foodid").toString());
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    /*减去对应商品的库存*/
    private void subStock(String count, String foodid) {
        List<Map<String, Object>> list = orderMapper.getAllStockInfo(foodid);
        list.stream().forEach(i->{
            BigDecimal totalcost = new BigDecimal(i.get("costnum").toString()).multiply(new BigDecimal(count));
            i.put("costnum", totalcost.toString());
            orderMapper.subStock(i);
        });
    }

}
