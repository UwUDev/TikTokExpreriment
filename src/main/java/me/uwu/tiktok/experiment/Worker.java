package me.uwu.tiktok.experiment;

import me.uwu.tiktok.experiment.struct.Config;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import xyz.downgoon.snowflake.Snowflake;

public class Worker {
    private final Config config;
    private final OkHttpClient client = new OkHttpClient();

    private static final Snowflake snowflake = new Snowflake(2, 5);
    private final RequestBody formBody;

    public Worker(Config config) {
        this.config = config;
        formBody = new FormBody.Builder()
                .add("item_id", String.valueOf(config.getVideoID()))
                .add("play_delta", "1")
                .build();
    }

    public boolean exec() {
        try {
            Request request = new Request.Builder()
                    .url("https://api.toutiao50.com/aweme/v1/aweme/stats/?channel=googleplay&device_type=" + config.getDevice().getName() + "&device_id=" + snowflake.nextId() + "&os_version=" + config.getDevice().getOsVersion() + "&version_code=" + config.getTikTokApp().getVersion() + "&app_name=musically_go&device_platform=android&aid=" + config.getTikTokApp().getAid())
                    .post(formBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("User-Agent", "com.zhiliaoapp.musically.go/" + config.getTikTokApp().getVersion() + " (Linux; U; Android " + config.getDevice().getOsVersion() + "; " + config.getTikTokApp().getLanguage() + "; " + config.getDevice().getName() + "; Build/MMB25K.G9250ZTU5DPC5; Cronet/TTNetVersion:5f9540e5 2021-05-20 QuicVersion:47555d5a 2020-10-15)")
                    .build();

            if (client.newCall(request).execute().body().string().contains("\"status_code\":0"))
                return true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }
}
