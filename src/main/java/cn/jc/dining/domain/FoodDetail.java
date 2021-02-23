package cn.jc.dining.domain;

import lombok.Data;

@Data
public class FoodDetail {
    private int id;
    private String name;
    private String picture;
    private String description;
    private String minPrice;
    private int typeId;
    private String stockdetail;
}
