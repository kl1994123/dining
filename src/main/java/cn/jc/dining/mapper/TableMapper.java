package cn.jc.dining.mapper;

import cn.jc.dining.domain.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableMapper {

    List<Table> getAllTable();

    void addNewTable(Table table);

    void updateTable(Table table);

    List<Table> getAllTableByTime(@Param("start") String start,@Param("end") String end);

    String getAllCount();

}
