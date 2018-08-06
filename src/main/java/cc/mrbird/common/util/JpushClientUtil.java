package cc.mrbird.common.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cc.mrbird.common.util.jpush.Alert;
import cc.mrbird.common.util.jpush.Result;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushClientUtil {
	private final static String appKey = "2bda10e172b84ce242c94c64";  
	
	private final static String masterSecret = "ae246734fecfc1c32a792494";
	
	private static JPushClient jPushClient = new JPushClient(masterSecret,appKey);
	

	/**
     * 发送给所有用户
     * @param alertMap  alert配置
     * @param extraJsonObject   额外增加字段
     * @return 0推送失败，1推送成功
     */
    public static Result sendToAll(String detail, JsonObject extraJsonObject) {
        Result result = new Result();
        try {
            PushPayload pushPayload= buildPushObject_android_and_ios(detail, extraJsonObject);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            result.setError_no(String.valueOf(pushResult.statusCode));
            result.setError_msg(String.valueOf(pushResult.msg_id));
            
        } catch (Exception e) {
 
            e.printStackTrace();
        }
 
        return result;
    }
    
    
    public static PushPayload buildPushObject_android_and_ios(String detail, JsonObject extraJsonObject) {
    	  
        System.out.println("----------buildPushObject_android_and_ios_alertWithTitle");
        extraJsonObject.addProperty("customId",getCustomId());
        
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(detail)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(detail)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra", extraJsonObject)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns body、title、subtitle等
                                .setAlert("{title: '"+detail+"'}")
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra", extraJsonObject)
                                //允许后台推送
                                .setContentAvailable(true)
                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(detail.toString())
                        .setTitle(extraJsonObject.toString())
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        //.setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(864000)
                        .build()
                )
                .build();
    }

    
    public static String getCustomId() {
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmmssS");
        int i = (int)(Math.random()*100);
        String customId = String.format("%s%d", df.format(new Date()), i);
        return customId;
    }

}
