package cn.jc.dining.mapper;

import cn.jc.dining.domain.Order;
import cn.jc.dining.domain.OrderDetail;
import cn.jc.dining.util.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<Order> getAllOrder(@Param("start") String start,
                            @Param("end") String end);

    List<Order> getAllOrderByTime(@Param("page") int page,
                                  @Param("rows")  int rows,
                                  @Param("start")  String start,
                                  @Param("end") String end);

    List<Order> getOrderById(@Param("openid") String openid);

    List<OrderDetail> getOrderDetail(@Param("ordernum") String ordernum);

    String getAllCount();

    void insertOrder(Order order);

    int deleteOrder(@Param("ordernum") String ordernum);

    List<Map<String,Object> >getAllOrderDetail(String now);

    int finalFood(@Param("id") String id);

    List<Map<String, Object>> getAllStockInfo(@Param("foodid") String foodid);

    void subStock(Map<String, Object> i);
}
