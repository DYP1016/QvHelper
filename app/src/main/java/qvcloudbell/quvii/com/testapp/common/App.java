package qvcloudbell.quvii.com.testapp.common;

import android.app.Application;
import android.os.Environment;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.File;

import qvcloudbell.quvii.com.testapp.R;

public class App extends Application {
    private static App instance;
    private String rootPath;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FlowManager.init(this);

        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getString(R.string.app_name);
    }

    public static App getInstance() {
        return instance;
    }

    public String getRootPath() {
        return rootPath;
    }
}
