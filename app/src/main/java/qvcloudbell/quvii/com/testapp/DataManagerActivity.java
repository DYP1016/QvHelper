package qvcloudbell.quvii.com.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qvcloudbell.quvii.com.testapp.bean.Record;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.common.Code;
import qvcloudbell.quvii.com.testapp.common.RecordDataManager;
import qvcloudbell.quvii.com.testapp.database.AppDatabase;
import qvcloudbell.quvii.com.testapp.utils.LogUtil;
import qvcloudbell.quvii.com.testapp.utils.PermissionUtils;

public class DataManagerActivity extends BaseActivity {

    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_time_old)
    TextView tvTimeOld;
    @BindView(R.id.tv_time_new)
    TextView tvTimeNew;
    @BindView(R.id.tv_mask_sum_count)
    TextView tvMaskSumCount;

    private List<Record> recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager);
        ButterKnife.bind(this);

        setTitle("数据管理");
        queryRecord();
    }

    @OnClick({R.id.tv_export_all, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_export_all:
                PermissionUtils.ExternalStorage(DataManagerActivity.this, isSuccess -> {
                    if (isSuccess) {
                        exportAll();
                    }
                });
                break;
            case R.id.tv_clear:
                new AlertDialog.Builder(context)
                        .setTitle("确认清除所有记录?")
                        .setPositiveButton("确认", (dialogInterface, i) -> {
                            AppDatabase.clearRecord();
                            queryRecord();
                        })
                        .show();
                break;
        }
    }

    private void queryRecord() {
        LogUtil.i("queryRecord");
        showLoading();
        recordList.clear();
        recordList.addAll(AppDatabase.getRecordList());

        tvCount.setText(String.valueOf(recordList.size()));

        if (recordList.size() > 0) {
            tvTimeOld.setText(recordList.get(recordList.size() - 1).getDateInfo());
            tvTimeNew.setText(recordList.get(0).getDateInfo());
        } else {
            tvTimeOld.setText("无");
            tvTimeNew.setText("无");
        }

        int maskSum = 0;
        for (Record record : recordList) {
            maskSum += record.getMask();
        }

        tvMaskSumCount.setText(String.valueOf(maskSum));

        hideLoading();
    }

    private void exportAll() {
        showLoading();
        RecordDataManager.getInstance().exportAllRecord(code -> {
            hideLoading();
            if (code == Code.SUCCESS) {
                showMessage("导出成功");
            } else {
                showMessage("导出失败: " + code);
            }
        });
    }
}
