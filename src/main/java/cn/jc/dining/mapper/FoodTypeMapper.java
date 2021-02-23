package cn.jc.dining.mapper;

import cn.jc.dining.domain.FoodDetail;
import cn.jc.dining.domain.FoodType;
import cn.jc.dining.domain.StockName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FoodTypeMapper {
    List<FoodType> getAllFood();

    FoodDetail getById(@Param("id") String id);

    List<Map> getAllManage(@Param("name") String name);

    List<FoodDetail> foodByName(@Param("name") String name);

    List<Map> selfFood(String openid);

    void deleteById(@Param("id") String id);

    List<Map<String,Object>> getFoodType();

    List<Map> getStock();

    Map getStockById(String id);

    void insertPic(@Param("id") String id,@Param("filePath")  String filePath);

    Map getFoodStock(String id);

    String getStockIdByName(@Param("stockname") String stockname);

    String getFoodIdByName(@Param("foodname") String foodname);

    void insertfoodstock(@Param("stockid") String stockid,
                         @Param("costnum")  String costnum,
                         @Param("foodid") String foodid);

    List<Map<String, Object>> loadstock(@Param("foodid")String foodid);

    void updatefoodstock(@Param("stockid") String stockid,
                         @Param("costnum")  String costnum,
                         @Param("foodid") String foodid);

    int FoodStockIsExist(@Param("foodid") String foodid);

    int addFood(Map map);

    String getAllCount();
}
