package cn.jc.dining.service.impl;

import cn.jc.dining.domain.FoodDetail;
import cn.jc.dining.domain.FoodType;
import cn.jc.dining.domain.Page;
import cn.jc.dining.mapper.FoodTypeMapper;
import cn.jc.dining.mapper.StockMapper;
import cn.jc.dining.service.FoodService;
import cn.jc.dining.util.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodTypeMapper foodTypeMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Map> getAll() {
        List<FoodType> allFood = foodTypeMapper.getAllFood();
        List<Map> foodlist = new ArrayList<>();
        allFood.stream().forEach(i -> {
            Map<String, Object> food = new HashMap<>();
            food.put("id", "t" + String.valueOf(i.getId()));
            food.put("className", i.getName());
            List<Map> fooddetaillist = new ArrayList<>();
            i.getSpus().stream().forEach(s -> {
                HashMap<String, Object> fooddetail = new HashMap<>();
                fooddetail.put("standardPrice", s.getMinPrice());
                fooddetail.put("id", String.valueOf(s.getId()));
                fooddetail.put("classId", "t" + String.valueOf(s.getTypeId()));
                fooddetail.put("image", s.getPicture());
                fooddetail.put("name", s.getName());
                fooddetaillist.add(fooddetail);
            });
            food.put("goods", fooddetaillist);
            foodlist.add(food);
        });
        return foodlist;
    }

    @Override
    public FoodDetail getById(String id) {
        return foodTypeMapper.getById(id);
    }

    @Override
    public Result getAllManage(Page page, String name) {
        Result result = new Result();
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Map> all = foodTypeMapper.getAllManage(name);
        List<Map> allStockType = stockMapper.getStock();
        Map<String, Object> map = new HashMap<>();
        allStockType.stream().forEach(i -> {
            map.put(i.get("id").toString(), i.get("name").toString());
        });
        all.stream().forEach(m -> {
            String[] strings = m.get("stockdetail").toString().split(",");
            String stock = "";
            for (String s : strings) {
                String stock1 = (String) map.get(s);
                stock += stock1;
            }
            m.put("stockdetail", stock);
        });
        result.setData(all);
        result.setMsg(foodTypeMapper.getAllCount());
        return result;
    }

    @Override
    public Result foodByName(Page page, String name) {
        Result result = new Result();
        PageHelper.startPage(page.getPage(), page.getRows());
        List<FoodDetail> list = foodTypeMapper.foodByName(name);
        result.setData(list);
        result.setMsg(String.valueOf(list.size()));
        return result;
    }


    @Override
    public Result deleteById(String id) {
        Result result = new Result();
        try {
//            foodTypeMapper.deleteById(id);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result selfFood(String openid) {
        Result result = new Result();
        try {
            result.setData(foodTypeMapper.selfFood(openid));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getFoodType() {
        return foodTypeMapper.getFoodType();
    }

    @Override
    public List<Map> getStock(String stockdetail) {
        List<Map> stock = foodTypeMapper.getStock();
        if (stockdetail != null) {
            String[] split = stockdetail.split(",");
            for (Map m : stock) {
                String stockList1 = JSON.toJSONString(m.get("stockList"));
                List<Map> stockList = JSONArray.parseArray(stockList1, Map.class);
                for (Map map : stockList) {
                    if (Arrays.asList(split).contains(map.get("id").toString())) {
                        map.put("selected", true);
                    }
                    m.put("stockList", map);
                }
                m.put("stockList", stockList);
            }
        }
        return stock;
    }

    @Override
    public void insertPic(String id, String filePath) {
        foodTypeMapper.insertPic(id, filePath);
    }

    @Override
    public List<Object> getFoodStock(String id) {
        Map<String, Object> food = foodTypeMapper.getFoodStock(id);
        String[] split = food.get("stockdetail").toString().split(",");
        String name = food.get("name").toString();
        List<Object> list = new ArrayList<>();
        for (String s : split) {
            Map stock = foodTypeMapper.getStockById(s);
            Map<Object, Object> map = new HashMap<>();
            map.put("id", stock.get("id"));
            map.put("name", stock.get("name"));
            list.add(map);
        }
        list.add(name);
        return list;
    }

    @Override
    public Result addstocknum(Map map) {
        Result result = new Result();
        try {
            String foodid = foodTypeMapper.getFoodIdByName(map.get("name").toString());
            Iterator iterator = map.keySet().iterator();
            if (foodTypeMapper.FoodStockIsExist(foodid) == 0) {
//              true代表不存在，就进行添加
                while (iterator.hasNext()) {
                    String stockname = iterator.next().toString();
                    if (!"name".equals(stockname)) {
                        String stocknum = map.get(stockname).toString();
                        String stockid = foodTypeMapper.getStockIdByName(stockname);
                        foodTypeMapper.insertfoodstock(stockid, stocknum, foodid);
                    }
                }
                result.setSuccess(true);
            }else{
                while (iterator.hasNext()) {
                    String stockname = iterator.next().toString();
                    if (!"name".equals(stockname)) {
                        String stocknum = map.get(stockname).toString();
                        String stockid = foodTypeMapper.getStockIdByName(stockname);
                        foodTypeMapper.updatefoodstock(stockid, stocknum, foodid);
                    }
                }
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            System.out.println(e.getMessage());
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> loadstock(String id) {
        return foodTypeMapper.loadstock(id);
    }

    @Override
    public Result addFood(Map map) {
        Result result = new Result();
        if(foodTypeMapper.addFood(map)!=0){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }


}
