package cn.jc.dining.domain;

import lombok.Data;

import java.util.List;

@Data
public class FoodType {
    private int id;
    private String name;
    private String icon;
    private List<FoodDetail> spus;
}
