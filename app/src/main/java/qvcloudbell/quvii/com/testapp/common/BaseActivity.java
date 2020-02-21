package qvcloudbell.quvii.com.testapp.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import qvcloudbell.quvii.com.testapp.R;

public class BaseActivity extends AppCompatActivity {
    protected Context context;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
    }

    public void showMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(int res) {
        showMessage(getString(res));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        context = null;
    }

    public void showLoading() {
        initProgressDialog();
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.publico_custom_progress_dlg);
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initProgressDialog() {
        if (null == this.mProgressDialog) {
            ProgressDialog progressDialog = new ProgressDialog(context, R.style.CustomDialog);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.mProgressDialog = progressDialog;
        }
    }

    protected void setTitle(String info) {
        getSupportActionBar().setTitle(info);
    }

    protected void setSubTitle(String info) {
        getSupportActionBar().setSubtitle(info);
    }

    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    protected void startTargetActivity(Class<?> target) {
        startActivity(new Intent(this, target));
    }
}
