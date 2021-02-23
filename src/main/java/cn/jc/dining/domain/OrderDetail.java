package cn.jc.dining.domain;

import lombok.Data;

@Data
public class OrderDetail {
    private int id;
    private long orderId;
    private String foodId;
    private int foodCount;
    private int status;
}
