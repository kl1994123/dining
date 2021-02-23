package cn.jc.dining.controller;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Stock;
import cn.jc.dining.mapper.StockMapper;
import cn.jc.dining.service.StockService;
import cn.jc.dining.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockMapper stockMapper;


    @RequestMapping("/stock-list")
    public String stocklist(){
        return "stock-list";
    }

    @RequestMapping("/stock-detail")
    public String stockdetail(){
        return "stock-detail";
    }

    @RequestMapping("/stock-add")
    public String stockadd(){
        return "stock-add";
    }

    //获得所有库存
    @GetMapping("/getAllManage")
    @ResponseBody
    public Result getAllStock(Page page,String name) throws ParseException {
        return stockService.getAllStock(page,name);
    }

    @GetMapping("/count")
    @ResponseBody
    public String count() {
        return stockMapper.getAllCount();
    }

//    获得库存分类
    @GetMapping("/type")
    @ResponseBody
    public Result getAllStockType() throws ParseException {
        return stockService.getAllStockType();
    }

    //    获得库存分类
    @GetMapping("/stock/{id}")
    @ResponseBody
    public Result stockById(@PathVariable("id")String id) throws ParseException {
        return stockService.stockById(id);
    }


    //    新增库存
    @PostMapping("/stock")
    @ResponseBody
    @Transactional
    public Result addStock(Stock stock){
        Result result = stockService.addStock(stock);
        return result;
    }

    //    删除库存
    @DeleteMapping("/stock/{id}")
    @ResponseBody
    public Result deleteById(@PathVariable("id") int id) throws ParseException {
        return stockService.deleteById(id);
    }

    //    获得预警
    @GetMapping("/warning")
    @ResponseBody
    public List<String> warning(){
        return stockService.warning();
    }


}
