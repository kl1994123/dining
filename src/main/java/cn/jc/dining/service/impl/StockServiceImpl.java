package cn.jc.dining.service.impl;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Stock;
import cn.jc.dining.mapper.StockMapper;
import cn.jc.dining.service.StockService;
import cn.jc.dining.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;


    @Override
    public Result getAllStock(Page page,String name) {
        Result result = new Result();
        int pagesize = (page.getPage() - 1)*page.getRows();
        List<Map> list = stockMapper.getAllStock(pagesize, page.getRows(),name);
        result.setData(list);
        result.setMsg(stockMapper.getAllCount());
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result getAllStockType() {
        Result result = new Result();
        List<Map> list = stockMapper.getAllStockType();
        System.out.println(list);
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result addStock(Stock stock) {
        Result result = new Result();
        Integer exist = stockMapper.isExist(stock.getName());
        //新商品
        if(exist == null) {
            stockMapper.addNewStock(stock);
//            新增库存明细
            stockMapper.addNewStockDetail(stock.getId(),stock.getStocknum());
            result.setSuccess(true);
        }else{
//            已有商品增加库存
            stockMapper.addStock(stock);
            stockMapper.addStockDetail(exist,stock.getStocknum());
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public Result deleteById(int id) {
        Result result = new Result();
        try {
            stockMapper.deleteById(id);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result stockById(String id) {
        Result result = new Result();
        Map map = stockMapper.stockById(id);
        result.setData(map);
        return result;
    }

    //返回预警id
    @Override
    public List warning() {
        return stockMapper.warning();
    }

}
