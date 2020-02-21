package qvcloudbell.quvii.com.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qvcloudbell.quvii.com.testapp.bean.Record;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.database.AppDatabase;
import qvcloudbell.quvii.com.testapp.utils.LogUtil;

public class RecordActivity extends BaseActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;

    private RecordAdapter recordAdapter;
    private List<Record> recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        setTitle("记录列表");

        recordAdapter = new RecordAdapter(recordList);
        recordAdapter.setItemClickListener(record -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(EditActivity.INTENT_RECORD_ID, record.getId());
            startActivityForResult(intent, 0);
        });

        rvMain.setAdapter(recordAdapter);
        rvMain.setLayoutManager(new LinearLayoutManager(context));

        srlMain.setOnRefreshListener(this::queryRecord);

        queryRecord();
    }

    private void queryRecord() {
        LogUtil.i("queryRecord");
        setRefresh(true);
        recordList.clear();
        recordList.addAll(AppDatabase.getRecordList());

        LogUtil.i("record size = " + recordList.size());

        recordAdapter.notifyDataSetChanged();
        setRefresh(false);
        setSubTitle("记录数: " + recordList.size());
    }

    private void setRefresh(boolean enable) {
        srlMain.setRefreshing(enable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            queryRecord();
        }
    }

    public class RecordAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Record> recordList;
        private ItemClickListener itemClickListener;

        public RecordAdapter(List<Record> recordList) {
            this.recordList = recordList;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Record record = recordList.get(position);

            holder.tvName.setText(record.getName());

            StringBuilder builder = new StringBuilder();
            if (record.getTemperature() > 0) {
                builder.append("体温: ").append(record.getTemperature()).append("摄氏度");
            }
            if (record.getMask() > 0) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }

                builder.append("领取了").append(record.getMask()).append("个口罩");
            }

            holder.tvInfo.setText(builder.toString());

            holder.tvTime.setText(record.getDateInfo());

            holder.clBackground.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(record);
                }
            });
        }

        @Override
        public int getItemCount() {
            return recordList.size();
        }

    }

    public interface ItemClickListener {
        void onItemClick(Record record);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvInfo;
        TextView tvTime;
        ConstraintLayout clBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvTime = itemView.findViewById(R.id.tv_time);
            clBackground = itemView.findViewById(R.id.cl_background);
        }
    }
}
