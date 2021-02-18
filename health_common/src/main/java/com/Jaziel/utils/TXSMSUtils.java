package com.Jaziel.utils;

import com.aliyuncs.exceptions.ClientException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

/**
 * 短信发送工具类
 */
public class TXSMSUtils {
    // 短信模板 ID，需要在短信应用中申请
    public static final int TEMPLATE_ID = 869799;//发送短信验证码
    public static final int ORDER_NOTICE = 869800;//体检预约成功通知

    public static void sendShortMessage(int templateId, String phoneNumbers, String param) throws ClientException {
        // 短信应用 SDK AppID
        int appid = 1400485519; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "0d7e111e23eec3fe727ffecba6a9a856";
        // 签名
        String smsSign = "Jaziel"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
        try {

            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers,
                    templateId, new String[]{param}, smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            TXSMSUtils.sendShortMessage(TEMPLATE_ID, "16645123195", "5623");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
