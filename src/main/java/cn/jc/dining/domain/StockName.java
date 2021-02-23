package cn.jc.dining.domain;

import lombok.Data;

import java.util.List;

@Data
public class StockName {
    private int id;
    private String typename;
    private List<Stock> stockList;
}
