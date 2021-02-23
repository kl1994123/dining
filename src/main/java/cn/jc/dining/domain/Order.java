package cn.jc.dining.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private int id;
    private String openid;
    private String orderId;
    private int state;
    private int status;
    private BigDecimal total;
    private String orderTime;

}
