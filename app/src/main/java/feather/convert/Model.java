package feather.convert;

import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by feather on 2017/6/20.
 */

public class Model {

    public ArrayList<Data> list = new ArrayList<Data>();
    OkHttpClient client;
    String URL = "", title_conversion = "";
    Presenter presenter;
    Handler handler = new Handler();

    public Model(Presenter presenter) {
        this.presenter = presenter;
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(10, TimeUnit.SECONDS);    //限制讀的秒數
        client = b.build();
        title_conversion = presenter.view.getResources().getString(R.string.title_conversion);
    }

    public void getData() {
        list.clear();
//         開源國際匯率 api ( json格式
        URL = "https://tw.rter.info/capi.php";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(URL);
                Request request = builder.build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String message = response.body().string();
                        Log.i("data", "response message = " + message);
                        try {
                            JSONObject jsonObject = new JSONObject(message);
                            JSONArray jsonArray = new JSONObject(message).names();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonObject.getJSONObject(jsonArray.getString(i).toString());
                                if (!jsonArray.getString(i).toString().trim().equals("USDUSD")) {
                                    //已經有個美金了不需要再美金轉美金
                                    Data content = new Data();
                                    content.setName(jsonArray.getString(i).toString());
                                    content.setExrate(Float.valueOf(obj.getString("Exrate")));
                                    list.add(content);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
//                                預設比率1,台幣置頂
                                presenter.notifyData(1, title_conversion);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });
            }
        }).start();
    }
}
