package qvcloudbell.quvii.com.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.ScannerConfig;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScanActivity extends CaptureActivity {
    public static final String SCAN_RESULT = "result";

    static {
        ScannerConfig.IS_VIBRATE = true;
        ScannerConfig.IS_PLAY_BEEP = true;
    }

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan;
    }

    @Override
    protected int getViewfinderViewId() {
        return R.id.viewfinder_view;
    }

    @Override
    protected int getPreviewViewId() {
        return R.id.preview_view;
    }

    @Override
    protected void onDecode(String resultString) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(SCAN_RESULT, resultString);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);

        finish();
    }

    @OnClick(R.id.btn_cancel_scan)
    public void onViewClicked() {
        finish();
    }
}
