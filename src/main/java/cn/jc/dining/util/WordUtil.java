//package cn.jc.dining.util;
//
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.ComThread;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//
//import java.io.*;
//import java.util.Map;
//
///**
// * @Desc：word操作工具类
// * @Author：张轮
// * @Date：2014-1-22下午05:03:19
// */
//public class WordUtil {
//    private static final int WDFO_RMATPDF = 17;
//
//    /**
//     * @param dataMap  word中需要展示的动态数据，用map集合来保存
//     * @param filePath 文件生成的目标路径，例如：D:/wordFile/
//     * @Desc：生成word文件
//     * @Author：张轮
//     * @Date：2014-1-22下午05:33:42
//     */
//    @SuppressWarnings("unchecked")
//    public static void createWord(Map dataMap, String filePath) {
//        try {
//            //创建配置实例
//            Configuration configuration = new Configuration();
//
//            //设置编码
//            configuration.setDefaultEncoding("UTF-8");
//
//            //ftl模板文件统一放至 com.lun.template 包下面
//            configuration.setClassForTemplateLoading(WordUtil.class, "/template/");
//
//            //获取模板
//            Template template = configuration.getTemplate("ftm.ftl");
//
//            //输出文件
//            File outFile = new File(filePath);
//
//            //如果输出目标文件夹不存在，则创建
//            if (!outFile.getParentFile().exists()) {
//                outFile.getParentFile().mkdirs();
//            }
//
//            //将模板和数据模型合并生成文件
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
//
//
//            //生成文件
//            template.process(dataMap, out);
//
//            //关闭流
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void printWord(String filePath) {
////        初始化线程
//        ComThread.InitSTA();
//        ActiveXComponent word = new ActiveXComponent("Word.Application");
//        //设置打印机名称
//        word.setProperty("ActivePrinter", new Variant("58MBIII"));
//        // 这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
//        Dispatch.put(word, "Visible", new Variant(false));
//        // 获取文档属性
//        Dispatch document = word.getProperty("Documents").toDispatch();
//        // 打开激活文挡
//        Dispatch doc = Dispatch.call(document, "Open", filePath).toDispatch();
//        //Dispatch doc = Dispatch.invoke(document, "Open", Dispatch.Method,
//        //  new Object[] { filePath }, new int[1]).toDispatch();
//        try {
//            Dispatch.call(doc, "PrintOut");
//            System.out.println("打印成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("打印失败");
//        } finally {
//            try {
//                if (doc != null) {
//                    Dispatch.call(doc, "Close", new Variant(0));//word文档关闭
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//            //退出
//            word.invoke("Quit", new Variant[0]);
//            //释放资源
//            ComThread.Release();
//            ComThread.quitMainSTA();
//        }
//
//    }
//
//
//    public static boolean wordToPdf(String wordPath, String pdfPath) {
//        ActiveXComponent msWordApp = new ActiveXComponent("Word.Application");
//        msWordApp.setProperty("Visible", new Variant(false));
//        Dispatch docs = Dispatch.get(msWordApp, "Documents").toDispatch();
//        Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{wordPath, new Variant(false), new Variant(true)}, new int[1]).toDispatch();
//        Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{pdfPath, new Variant(WDFO_RMATPDF)}, new int[1]);
//        if (null != doc) {
//            Dispatch.call(doc, "Close", false);
//        }
//        return true;
//    }
//}