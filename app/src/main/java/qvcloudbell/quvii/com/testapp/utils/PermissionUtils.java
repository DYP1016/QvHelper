package qvcloudbell.quvii.com.testapp.utils;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;

public class PermissionUtils {

    public static void LauncherCamera(BaseActivity activity, CallBack callBack) {
        RxPermissions permissions = new RxPermissions(activity);

        permissions.request(Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!aBoolean) {
                            activity.showMessage("未有摄像头权限, 请授予摄像头权限");
                        }
                        callBack.onResult(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        activity.showMessage("授权失败: " + e.toString());
                        callBack.onResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void ExternalStorage(BaseActivity activity, CallBack callBack) {
        RxPermissions permissions = new RxPermissions(activity);

        permissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!aBoolean) {
                            activity.showMessage("未有外部存储权限, 请授予权限");
                        }
                        callBack.onResult(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        activity.showMessage("授权失败: " + e.toString());
                        callBack.onResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public interface CallBack {
        void onResult(boolean isSuccess);
    }
}
