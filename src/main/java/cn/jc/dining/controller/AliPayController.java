package cn.jc.dining.controller;

import cn.jc.dining.util.Result;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/alipay")
public class AliPayController {
    @PostMapping("/pay")
    @ResponseBody
    public Result pay(@RequestBody Map<String,Object> map){
        System.out.println(map);
        Result result = new Result();
// 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradeAppPayResponse response = Factory.Payment.App()
                    .pay(map.get("subject").toString(), map.get("outTradeNo").toString(), map.get("totalAmount").toString());
            // 3. 处理响应或异常
            System.out.println(response.body);
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                result.setMsg(response.body);
                result.setSuccess(true);
                return result;
            } else {
//                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            return result;
        }
        return null;
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = "2016101000653322";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHcXky4/7zgqSuS8nurbgD+8pr/kTz4LMU4+8SDj8t1kmfFIIx6Gmi7jCfIvOKxw0PSsOtBplPYSDNmwfXTsTV2dAe1BoLc1mAb27lQTJATgGXzdB6tD8YGb/4KMG5w3cvvH0wy8wDUOWrNEKG4mrTC2JUbTEd08jCqBKMQa1noWCMeZHFaBZlqy/3fQnCgzCzZgNYTQSgALrHe2bvoIY8fSMxLj/St0q13GH95mwNEhxXdi9RXqnSvmtB3A3hRNgzcIK75ruk8lcSz0R8sb+r6zlxx3YUAPFHuOvoV4n84/KCZ6FuuTvB/c+7i9PeVTnmGEwf34yIyFqOhUP5yNPzAgMBAAECggEAdgZ+28bsAxzEDpJCjDdbVDVUxliTk4Up6LlbOfuqOHI0Q2imr4DclLtFKVdWrIcaLUo0S5GlhROZdzFyWr/sLIIaptKjQ3kn9BCmrSXr+TdAkh0qX9Kz7Hd73m79qbfYI4P3/86HoBgdmtv2YP3qoq+X5wQjud80YdP+HbSZu+QPFc4FZAF39cade1E27CdA9xQUPnf5Ed3BEEbHpbm0lR8br9fRqYVe5DtO+ilimuLomJIRzhAYkz9Gki5PHUIXZgOokoDbKOoe6KlIMfh6V0Q2Z+kFqW50pFRO2rrFcAepx7apjT2sTsXRoSohJ0tcwFtBNpwEmz1rlOuMquzkEQKBgQDZH9fcIuRHvr+U5Zy4/V3QD9Hy2tkNLPCX8AEejk4eWiPuW8UmCyARoUBye+C4PsT+TjuZYLNuNvkVd+1b0a95hSwee2HB3+aIzJ7bkJb8LPfiXZZxa3HDootfZR+mEoMEUKp3pjMxyOgiKri1LR4P+HQ6L7Gcye0GU+nhrbxcmwKBgQCfsa10BIB7M6oIRIOARETTyJGtnj4eV3pBG8Rj7aU3jY5RV/Kc5VQZC3RjfpiM/USpLzv7jEIBDfMBVobnHpl1pj4xpq1f92FvKqkGe2YKHMFnoyMQy6GNaL9Z1gKarcXN9A47GaidnubsDdRAqPAtHeD0l0KsHC0zd9kyeVWfiQKBgGlXHa9b7k0wqlpfHC5UHpO3WTDuJKhTyVatxp1AhX4MVaprM7dFZoWnKlFg8KHzMjf/VMMRO3yZhzd3O910WiInDkSrr74UFxD/1YLJH7exSYK1zSux6Tv1PJFHypcUHCSXzlzGccgRpS4OdBbSkOdGr30bbpyXCzwqsiof1GdJAoGAObLZx+Rkg58sHSacGLkR4S9Dq2ZUbWQP8PxWXgCcRFEqDavoJdYaJ55i5Bv2hGJx72r4ki+gHX9rwJ0ByvncWozBRfFCDT11f9P7FDN7r8Wp/4cLAWOBsHUbRhsz/60vnfVDOwlPSfwC1WE9wksH1FVKVTVdyo+3qTPxTvJWBHkCgYAcGMcM49y7ujctZ2KIQJFo6CJNcEA1A9D70DGaRN+f1MPmWyCjuTFcMlszVPnA6mn1OiuInfwPJjqyHShIgukNwOkSPsxWI2ysTRFgR8xsFqw+DB/yd5jncN/qJg5BLEUbw4NxUg8wXA7VKc2gXECOLSYV+TC58w9y0je0VQcF1Q==";

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//        config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
//        config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//        config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr3j0suiWMmtGq8UukayScuFEFodl4l432jRtglDbAdJ1/v3vC0UtfN9mO+S3x3Kf4qU+aSbIfMoNzA1MkdcQAVvMsNTaxa1BxWA4wbaO9okKXDAgw7RiOlAF9/NG+5gZFix9ArdWEWx4xt1G4XO5hypEaI3q9O2wy4RosVrxod1OhIQVSaqH4Bs7DKr2Z9mXB+amJptpKjRoWIsRyWTACc5+yr2XUK1W5eKJTQke/xQuf8sGZ7omTqMCPqHvSV3E/U5oZl6fHt99f5bdhZmYpxcLSi8CovOspY0YMPtna74Wbpne98MK92C6vbAGBWwTC58VVTMpNixaJ8Af54fg2wIDAQAB";

        //可设置异步通知接收服务地址（可选）
//        config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//        config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }
}
