package qvcloudbell.quvii.com.testapp.common;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import qvcloudbell.quvii.com.testapp.bean.UserInfo;
import qvcloudbell.quvii.com.testapp.body.CardInfoBody;
import qvcloudbell.quvii.com.testapp.body.CardInfoBodyResult;

public class CardInfoManager {
    private static final String TAG = "CardInfoManager";
    private static OkHttpClient client;

    private CardInfoManager() {
    }

    public static CardInfoManager getInstance() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.i(TAG, "OkHttpClient log: " + message)))
                .build();

        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final CardInfoManager instance = new CardInfoManager();
    }

    /**
     * @param url      例如https://h5.dingtalk.com/zproject/profile.html?profile=%40kgDODg1OsQ&cardToken=43fbdb8656
     * @param listener 回调接口
     */
    public void getUserInfo(String url, LoadListener<UserInfo> listener) {
        Observable.create((ObservableOnSubscribe<QvResult<UserInfo>>) e -> {
            if (TextUtils.isEmpty(url)) {
                e.onNext(new QvResult<>(Code.FAIL_EMPTY));
                return;
            }

            String temp = url.substring(url.indexOf("?") + 1);
            Log.i(TAG, "getUserInfo: " + temp);

            String[] data = temp.split("&");
            if (data.length < 2) {
                e.onNext((new QvResult<>(Code.FAIL_DATA_ERROR)));
                return;
            }

            Map<String, String> info = generateMap(data);

            //构建请求体
            //"key=CardI%2FgetCardDetail2&args=%5B%22%40kgDODg1OsQ%22%2C%2243fbdb8656%22%5D";
            RequestBody body = RequestBody.create(MediaType.parse("Text"),
                    "CardI%2FgetCardDetail2&args=%5B%22" + info.get("profile")
                            + "%22%2C%22" + info.get("cardToken")
                            + "%22%5D"
            );

            //构建请求对象
            Request request = new Request.Builder()
                    .url("https://h5.dingtalk.com/unAuthLwp/getCardDetail2?_api=CardI.getCardDetail2")
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .post(body)
                    .build();

            //进行网络请求
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException ex) {
                    ex.printStackTrace();
                    e.onNext((new QvResult<>(Code.FAIL, ex.toString())));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.body() == null) {
                        e.onNext((new QvResult<>(Code.FAIL_DATA_ERROR)));
                        return;
                    }

                    String body = response.body().string();
                    Log.e(TAG, "onResponse: " + body);

                    //处理响应结果
                    Gson gson = new Gson();

                    CardInfoBody cardInfoBody = gson.fromJson(body, CardInfoBody.class);

                    if (!cardInfoBody.isSuccess()) {
                        e.onNext((new QvResult<>(Code.FAIL, cardInfoBody.getErrorMsg())));
                        return;
                    }

                    CardInfoBodyResult cardInfoBodyResult = gson.fromJson(cardInfoBody.getResult(), CardInfoBodyResult.class);

                    UserInfo userInfo = new UserInfo(
                            cardInfoBodyResult.getUid(),
                            cardInfoBodyResult.getName()
                    );

                    e.onNext((new QvResult<>(userInfo)));
                }
            });
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QvResult<UserInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QvResult<UserInfo> userInfoQvResult) {
                        listener.onResult(userInfoQvResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onResult(new QvResult<>(Code.FAIL, e.toString()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Map<String, String> generateMap(String[] input) {
        Map<String, String> map = new HashMap<>();

        for (String s : input) {
            try {
                String[] temp = s.split("=");
                map.put(temp[0], temp[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }
}
