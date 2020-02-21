package qvcloudbell.quvii.com.testapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qvcloudbell.quvii.com.testapp.bean.Record;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.database.AppDatabase;
import qvcloudbell.quvii.com.testapp.utils.LogUtil;
import qvcloudbell.quvii.com.testapp.utils.SpUtil;

public class EditActivity extends BaseActivity {

    public static final String INTENT_NAME = "intent_name";
    public static final String INTENT_RECORD_ID = "intent_record_id";

    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.et_temperature)
    TextInputEditText etTemperature;
    @BindView(R.id.et_mask)
    TextInputEditText etMask;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.bt_delete)
    Button btDelete;


    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        //判断是添加记录还是读取已有记录
        Long id = getIntent().getLongExtra(INTENT_RECORD_ID, -1);
        if (id >= 0) {
            setTitle("编辑记录");
            btDelete.setVisibility(View.VISIBLE);
            //读取已有记录
            record = AppDatabase.getRecord(id);
            if (record == null) {
                LogUtil.e("找不到对应的记录: " + id);
                finish();
                return;
            }

            etName.setText(record.getName());
            etTemperature.setText(String.valueOf(record.getTemperature()));
            etMask.setText(String.valueOf(record.getMask()));

        } else {
            //添加记录
            setTitle("添加记录");
            btDelete.setVisibility(View.GONE);

            etMask.setText(String.valueOf(SpUtil.getInstance().getDefaultMask()));

            record = new Record();
            record.setTime(System.currentTimeMillis());

            //检查是否已经有姓名
            String name = getIntent().getStringExtra(INTENT_NAME);
            if (!TextUtils.isEmpty(name)) {
                //已有姓名
                etName.setText(name);
                etName.setFocusable(false);

                etTemperature.requestFocus();
            } else {
                //未有姓名

                etName.requestFocus();
            }
        }


        setSubTitle("记录时间: " + record.getDateInfo());
        queryUserInfo();
    }

    @OnClick({R.id.bt_save, R.id.tv_info, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                save();
                break;
            case R.id.tv_info:
                queryUserInfo();
                break;
            case R.id.bt_delete:
                new AlertDialog.Builder(context)
                        .setTitle("确认删除此条记录?")
                        .setPositiveButton("确认", (dialogInterface, i) -> {
                            record.delete();
                            setResult(1);
                            finish();
                        })
                        .show();
                break;
        }
    }

    private void save() {
        try {
            String name = etName.getText() != null ? etName.getText().toString() : "";
            if (TextUtils.isEmpty(name)) {
                showMessage("姓名不能为空");
                return;
            }
            record.setName(name);

            if (etTemperature.getText() == null || TextUtils.isEmpty(etTemperature.getText())) {
                record.setTemperature(0.0);
            } else {
                record.setTemperature(Double.parseDouble(etTemperature.getText().toString()));
            }

            if (etMask.getText() == null || TextUtils.isEmpty(etMask.getText())) {
                record.setMask(0);
            } else {
                record.setMask(Integer.parseInt(etMask.getText().toString()));
            }

            LogUtil.i("save record: " + record.toString());
            record.save();

            showMessage("保存成功");
            setResult(1);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("保存失败: " + e.toString());
        }
    }

    private void queryUserInfo() {
        if (TextUtils.isEmpty(etName.getText())) {
            return;
        }

        showLoading();
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map(aLong -> {
                    StringBuilder builder = new StringBuilder();
                    List<Record> recordList = AppDatabase.getRecordListWithName(etName.getText().toString());

                    int temperatureCount = 0;
                    int maskCount = 0;

                    for (Record record : recordList) {
                        if (record.getTemperature() > 0) {
                            temperatureCount++;
                        }

                        if (record.getMask() > 0) {
                            maskCount += record.getMask();
                        }
                    }

                    if (temperatureCount == 0 && maskCount == 0) {
                        builder.append("该同事未有任何记录");
                    } else {
                        builder.append("该同事今天测量了").append(temperatureCount).append("次体温, 领了")
                                .append(maskCount).append("个口罩");
                    }

                    return builder;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StringBuilder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StringBuilder stringBuilder) {
                        hideLoading();
                        tvInfo.setText(stringBuilder.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        hideLoading();

                        showMessage("查询失败: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
