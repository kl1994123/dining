package cn.jc.dining.mapper;

import cn.jc.dining.domain.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockMapper {

    List<Map> getAllStock(@Param("page") int pagesize,@Param("rows")  int rows,@Param("name")String name);

    List<Map> getStock();

    List<Map> getAllStockType();

    Integer isExist(@Param("name")String name);

    int   addNewStock(Stock stock);

    void addNewStockDetail(@Param("id")int id,@Param("stocknum") int stocknum);

    int addStock(Stock stock);

    void addStockDetail(@Param("id")int id,@Param("stocknum") int stocknum);

    void deleteById(@Param("id")int id);

    Map stockById(@Param("id") String id);

    List<String> warning();

    String getAllCount();
}
