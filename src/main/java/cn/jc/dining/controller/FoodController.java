package cn.jc.dining.controller;

import cn.jc.dining.domain.FoodDetail;
import cn.jc.dining.domain.Page;
import cn.jc.dining.mapper.FoodTypeMapper;
import cn.jc.dining.service.FoodService;
import cn.jc.dining.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodTypeMapper foodTypeMapper;


    @RequestMapping("/food-list")
    public String foodlist(){
        return "food-list";
    }

    @GetMapping("/{path}")
    public String foodadd(@PathVariable("path")String path){
        return path;
    }
    @GetMapping("/food-add")
    public String foodadd(){
        return "food-add";
    }


    @GetMapping("/selfFood/{openid}")
    @ResponseBody
    public Result selfFood(@PathVariable("openid")String openid){
        return foodService.selfFood(openid);
    }

    //后台菜单分页
    @GetMapping("/getAllManage")
    @ResponseBody
    public Result getAllManage(Page page,String name){
        return foodService.getAllManage(page,name);
    }

    //后台菜单分页
    @GetMapping("/count")
    @ResponseBody
    public String count(){
        return foodTypeMapper.getAllCount();
    }

    @GetMapping("/foodByDate")
    @ResponseBody
    public Result foodByDate(Page page,String name){
        return foodService.foodByName(page,name);
    }


    @GetMapping("/food")
    @ResponseBody
    public Result getAll(){
        Result result = new Result();
        List<Map> all = foodService.getAll();
        result.setData(all);
        return result;
    }

    @PostMapping("/food")
    @ResponseBody
    public Result addFood(@RequestParam Map m){
        return foodService.addFood(m);
    }

    @GetMapping("/food/{id}")
    @ResponseBody
    public Result getById(@PathVariable("id") String id){
        Result result = new Result();
        FoodDetail foodDetail = foodService.getById(id);
        result.setData(foodDetail);
        return result;
    }

    @DeleteMapping("/food/{id}")
    @ResponseBody
    public Result deleteById(@PathVariable("id") String id){
        return foodService.deleteById(id);
    }
    /*查找商品分类*/
    @GetMapping("/type")
    @ResponseBody
    public Result getFoodType(){
        Result result = new Result();
        List<Map<String,Object>> type = foodService.getFoodType();
        result.setData(type);
        return result;
    }
    /*查找库存*/
    @GetMapping("/stock")
    @ResponseBody
    public Result getStock(@RequestParam(value = "stockdetail",required = false) String stockdetail){
        Result result = new Result();
        List<Map> type = foodService.getStock(stockdetail);
        result.setData(type);
        return result;
    }

    /*查找对应商品使用用料*/
    @GetMapping("/getFoodStock/{id}")
    @ResponseBody
    public Result getFoodStock(@PathVariable("id") String id){
        Result result = new Result();
        List<Object> list = foodService.getFoodStock(id);
        //这个list中，最后一位存的是菜名，其他都是用料名
        result.setData(list);
        return result;
    }

    /*添加对应商品使用用料*/
    @PostMapping("/addstocknum")
    @ResponseBody
    public Result addstocknum(@RequestParam Map map){
        return foodService.addstocknum(map);
    }

    /*查找对应商品使用用料*/
    @GetMapping("/loadstock/{id}")
    @ResponseBody
    public Result loadstock(@PathVariable("id") String id){
        Result result = new Result();
        List<Map<String,Object>> list = foodService.loadstock(id);
        result.setData(list);
        return result;
    }

/*上传图片*/
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Result uploadImg(MultipartFile file,@RequestParam Map map){
        Result result = new Result();
        System.out.println(map);

            // 文件保存路径
            String os = System.getProperty("os.name");
            //文件保存路径
            String filePath="";
            if(os.toLowerCase().startsWith("win")){
                //windows下的路径
                filePath ="d:/pictureUpload/dining/";
            }else {
                //linux下的路径
                filePath="/root/pictureUpload/dining/";
            }
            String s = String.valueOf(System.currentTimeMillis());//使用当前系统时间 避免文件名重复
            s += ".png";
        try {
            uploadFile(file.getBytes(), filePath, s);
            foodService.insertPic(map.get("id").toString(),s);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
