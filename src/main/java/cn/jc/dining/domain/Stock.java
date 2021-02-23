package cn.jc.dining.domain;

import lombok.Data;

@Data
public class Stock {
    private int id;
    private String name;
    private int stocknum;
    private int warningnum;
    private String price;
    private int typeid;
}
