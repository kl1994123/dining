package cn.jc.dining.domain;

import lombok.Data;

@Data
public class Table {
    private int tableNum;
    private int personNum;
    private String servicePerson;
    private String createDate;


}
