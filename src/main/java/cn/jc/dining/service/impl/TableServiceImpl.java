package cn.jc.dining.service.impl;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Table;
import cn.jc.dining.mapper.TableMapper;
import cn.jc.dining.service.TableService;
import cn.jc.dining.util.Result;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl  implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Override
    public Result getAllTable(Page page) {
        Result result = new Result();
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Table> allTable = tableMapper.getAllTable();
        result.setMsg(tableMapper.getAllCount());
        result.setData(allTable);
        return result;
    }

    @Override
    public Result tableByDate(Page page, String start, String end) {
        Result result = new Result();
        start +=" 00:00:00";
        end += " 23:59:59";
        PageHelper.startPage(page.getPage(),page.getRows());
        List<Table> list = tableMapper.getAllTableByTime(start,end);
        result.setData(list);
        result.setMsg(String.valueOf(list.size()));
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result addNewTable(Table table) {
        Result result = new Result();
        try {
            tableMapper.addNewTable(table);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result updateTable(Table table) {
        Result result = new Result();
        try {
            tableMapper.updateTable(table);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result getImg(String tableid) {
        return null;
    }
}
