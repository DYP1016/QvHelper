package qvcloudbell.quvii.com.testapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import qvcloudbell.quvii.com.testapp.common.App;

public class SpUtil {
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private String SP_NAME = "config";
    private static SpUtil instance;

    private static final String KEY_DEFAULT_MASK = "default_mask";

    private SpUtil(Context context) {
        this.context = context;
    }

    private SpUtil() {
    }

    public static SpUtil getInstance() {
        return getInstance(App.getInstance());
    }

    public static SpUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil(context);
                }
            }

        }
        return instance;
    }

    private SharedPreferences getSP() {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    private SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = getSP().edit();

        }
        return editor;
    }

    public void setValue(String key, String value) {
        getEditor().putString(key, value);
        getEditor().commit();
    }

    public void clear() {
        getEditor().clear();
        getEditor().commit();
    }

    public void remove(String key) {
        getEditor().remove(key);
        getEditor().commit();
    }

    private void setBooleanValue(String key, boolean value) {
        getEditor().putBoolean(key, value);
        getEditor().commit();
    }

    private boolean getBooleanValue(String key) {
        return getSP().getBoolean(key, false);

    }

    private void setIntValue(String key, int value) {
        getEditor().putInt(key, value);
        getEditor().commit();
    }

    private int getIntValue(String key) {
        return getSP().getInt(key, -1);
    }

    public String getValue(String key) {
        return getSP().getString(key, "");

    }

    private Long getLongValue(String key) {
        return getSP().getLong(key, -1);
    }

    private void setLongValue(String key, Long value) {
        getEditor().putLong(key, value);
        getEditor().commit();
    }

    public void setDefaultMask(int count) {
        setIntValue(KEY_DEFAULT_MASK, count);
    }

    public int getDefaultMask() {
        return getSP().getInt(KEY_DEFAULT_MASK, 1);
    }
}
