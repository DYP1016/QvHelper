package qvcloudbell.quvii.com.testapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import qvcloudbell.quvii.com.testapp.common.BaseFragment;
import qvcloudbell.quvii.com.testapp.utils.LogUtil;

public class MoreFragment extends BaseFragment {

    private List<SelectAdapter.Item> itemList = new ArrayList<>();
    private SelectAdapter adapter;

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.more_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    public void init() {

        addItem(
                R.drawable.ic_edit_black_24dp,
                "手动添加",
                "通过手动输入添加记录"
        );

        addItem(
                R.drawable.ic_description_black_24dp,
                "查询记录列表",
                "查看/修改记录"
        );

        addItem(
                R.drawable.ic_update_black_24dp,
                "数据管理",
                "管理/导出记录"
        );

        addItem(
                R.drawable.ic_settings_black_24dp,
                "应用设置",
                "默认选项修改"
        );

        adapter = new SelectAdapter(itemList);
        adapter.setItemClickListener(position -> {
            switch (itemList.get(position).getHint1()) {
                case "手动添加":
                    startTargetActivity(EditActivity.class);
                    break;
                case "查询记录列表":
                    startTargetActivity(RecordActivity.class);
                    break;
                case "数据管理":
                    startTargetActivity(DataManagerActivity.class);
                    break;
                case "应用设置":
                    startTargetActivity(ConfigActivity.class);
                    break;
                default:
                    break;
            }
        });

        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new LinearLayoutManager(context));
    }

    private void addItem(int iconRes, String hint1, String hint2) {
        SelectAdapter.Item item = new SelectAdapter.Item(iconRes, hint1, hint2);
        itemList.add(item);
    }
}
