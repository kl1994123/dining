package cn.jc.dining.controller;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.Table;
import cn.jc.dining.domain.User;
import cn.jc.dining.mapper.TableMapper;
import cn.jc.dining.service.TableService;
import cn.jc.dining.util.Result;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private TableMapper tableMapper;

    @RequestMapping("/table-list")
    public String tablelist(User user){
        return "table-list";
    }

    @RequestMapping("/table-img")
    public String tableimg(User user){
        return "table-img";
    }

    @RequestMapping("/table-add")
    public String tableadd(User user){
        return "table-add";
    }


    @RequestMapping("/goods")
    public String goods(User user){
        return "goods";
    }

    @RequestMapping("/pay")
    public String pay(User user){
        return "pay";
    }

    @GetMapping("/table")
    @ResponseBody
    public Result getAllTable(Page page){
        return tableService.getAllTable(page);
    }

    @GetMapping("/count")
    @ResponseBody
    public String getAllTable(){
        return tableMapper.getAllCount();
    }

    @GetMapping("/tableByDate")
    @ResponseBody
    public Result tableByDate(Page page,String start,String end){
        return tableService.tableByDate(page,start,end);
    }

    @PutMapping("/table")
    public Result addNewTable(Table table){
        return tableService.addNewTable(table);
    }

    @PostMapping("/table")
    public Result updateTable(Table table){
        return tableService.addNewTable(table);
    }

    @GetMapping("/getImg")
    @ResponseBody
    public String getImg(@RequestParam("id") String id) throws IOException, WriterException {
//        TODO 将手机点餐网页进行替换
        String text = "http://172.11.2.40:4396/table/goods?id="+id;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500,300);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(pngData);
        return encode;
    }

    //记录扫码点餐桌号
    Map<String, String> scan = new ConcurrentHashMap<>();
    @GetMapping("/bindTable")
    @ResponseBody
    public Result bindTable(@RequestParam("tableId") String tableId){
        Result result = new Result();
        if (scan.get(tableId)==null) {
            scan.put(tableId,tableId);
            result.setSuccess(true);
            result.setMsg("该桌未扫描");
        }else{
            result.setSuccess(false);
            result.setMsg("该桌已经扫描");
        }
        return result;
    }


}
