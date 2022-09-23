package kr.tracom.cm.fclt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kr.tracom.cm.support.http.CallbackEvent;
import kr.tracom.cm.support.scheuler.AbstractApiScheduler;
import kr.tracom.platform.attribute.bis.integration.bluemobile.AtBluemobileInAndOut;
import kr.tracom.platform.attribute.bis.integration.bluemobile.AtBluemobileInAndOutItem;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.service.config.PlatformConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FcltScheduler extends  AbstractApiScheduler{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //private List<String> vhcList;

    @Override
    public void getConfig() {
       
    }

    @Override
    public  void run() {
        logger.info("FcltScheduler() run");


        CallbackEvent callbackEvent = new CallbackEvent() {
            @Override
            @Transactional
            public void callBackMethod(Object result) {
                
            }
        };
        try {
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public Logger getLogger() {
        return logger;
    }
}
