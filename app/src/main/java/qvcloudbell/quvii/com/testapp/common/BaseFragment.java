package qvcloudbell.quvii.com.testapp.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import qvcloudbell.quvii.com.testapp.R;

public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected ProgressDialog mProgressDialog;
    private Unbinder mUnbinder;
    protected View rootView;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected void startTargetActivity(Class<?> target) {
        startActivity(new Intent(context, target));
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

    public void showMessage(String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(int res) {
        showMessage(getString(res));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        context = null;
    }
}
