package cn.jc.dining.service;


import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Stock;
import cn.jc.dining.util.Result;

import java.util.List;

public interface StockService {


    Result getAllStock(Page page,String name);

    Result getAllStockType();

    Result addStock(Stock stock);

    Result deleteById(int id);

    Result stockById(String id);

    List warning();
}
