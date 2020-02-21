package qvcloudbell.quvii.com.testapp;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.common.BaseFragment;
import qvcloudbell.quvii.com.testapp.utils.PermissionUtils;

public class MainFragment extends BaseFragment {

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    private void scannerQr() {
        PermissionUtils.LauncherCamera((BaseActivity) context, ret -> {
            if (ret) {
                startActivityForResult(new Intent(context, ScanActivity.class), 0);
            }
        });
    }

    @OnClick({R.id.ll_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main:
                scannerQr();
                break;
        }
    }
}
