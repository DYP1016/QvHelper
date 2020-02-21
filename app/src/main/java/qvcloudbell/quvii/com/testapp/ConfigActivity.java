package qvcloudbell.quvii.com.testapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.utils.SpUtil;

public class ConfigActivity extends BaseActivity {

    @BindView(R.id.tv_mask_count)
    TextView tvMaskCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);

        setTitle("应用版本号: " + BuildConfig.VERSION_NAME);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        setSubTitle("编译时间: " + simpleDateFormat.format(new Date(BuildConfig.BUILD_TIMESTAMP)));

        queryMaskDefaultCount();
    }

    @OnClick({R.id.tv_mask_count, R.id.ll_mask_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mask_count:
                break;
            case R.id.ll_mask_count:
                modifyMaskCount();
                break;
        }
    }

    private void modifyMaskCount() {
        TextInputEditText editText = new TextInputEditText(context);

        new AlertDialog.Builder(context)
                .setView(editText)
                .setTitle("请输入默认口罩数")
                .setPositiveButton("确认", (dialogInterface, i) -> {
                    boolean isSuccess = false;
                    try {
                        int count = Integer.parseInt(editText.getText().toString());
                        if (count >= 0) {
                            SpUtil.getInstance().setDefaultMask(count);
                            isSuccess = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    queryMaskDefaultCount();
                    showMessage(isSuccess ? "修改成功" : "修改失败, 请输入大于等于0的整数");
                })
                .show();

    }

    private void queryMaskDefaultCount() {
        String info = SpUtil.getInstance().getDefaultMask() + "个";
        tvMaskCount.setText(info);
    }
}
