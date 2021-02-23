package cn.jc.dining.service;

import cn.jc.dining.domain.FoodDetail;
import cn.jc.dining.domain.Page;
import cn.jc.dining.util.Result;

import java.util.List;
import java.util.Map;

public interface FoodService {
    List<Map> getAll();

    FoodDetail getById(String id);

    Result getAllManage(Page page,String name);

    Result foodByName(Page page, String name);

    Result deleteById(String id);

    Result selfFood(String openid);

    List<Map<String,Object>> getFoodType();

    List<Map> getStock(String stockdetail);

    void insertPic(String id, String filePath);

    List<Object> getFoodStock(String id);

    Result addstocknum(Map map);

    List<Map<String,Object>> loadstock(String id);

    Result addFood(Map map);
}
