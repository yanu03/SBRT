package kr.tracom.cm.support.scheuler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.cm.domain.Intg.IntgMapper;
import kr.tracom.cm.support.http.ApiException;
import kr.tracom.cm.support.http.ApiParam;
import kr.tracom.cm.support.http.CallbackEvent;
import kr.tracom.cm.support.http.HttpClientApi;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class AbstractApiScheduler {

    protected HttpClientApi httpClientApi;
    protected ThreadPoolTaskScheduler scheduler;
    protected String cron = "0 0/1 * * * *";

    @Autowired
    protected IntgMapper intgMapper;

    public abstract Logger getLogger();
    public abstract void run();
    public abstract void getConfig();

    protected void changeCronSet(String cron) {
        this.cron = cron;
    }

    @PostConstruct
    protected void init() {
        getConfig();
        startScheduler();
    }

    @PreDestroy
    public void destroy() {
        stopScheduler();
    }

    public void startScheduler() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.schedule(getRunnable(), getTrigger());
    }

    protected void stopScheduler() {
        scheduler.shutdown();
    }



    protected Runnable getRunnable() {
        return () -> {
            run();
        };
    }

    private Trigger getTrigger() {
        return new CronTrigger(cron);
    }

    protected void httpRequest(CallbackEvent callbackEvent, ApiParam apiParam) throws ApiException {
        List<String> resultList = new ArrayList<>();

        httpClientApi = new HttpClientApi();
        String result = (String) httpClientApi.get(apiParam);
        resultList.add(result);

        callbackEvent.callBackMethod(resultList);
    }

    protected Gson getGson() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        return gson;
    }

    private class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    private class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
