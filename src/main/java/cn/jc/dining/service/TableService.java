package cn.jc.dining.service;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Table;
import cn.jc.dining.util.Result;

public interface TableService {

    Result getAllTable(Page page);

    Result tableByDate(Page page, String start, String end);

    Result addNewTable(Table table);

    Result updateTable(Table table);

    Result getImg(String tableid);
}
