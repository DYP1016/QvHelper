package qvcloudbell.quvii.com.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qvcloudbell.quvii.com.testapp.common.BaseActivity;
import qvcloudbell.quvii.com.testapp.common.CardInfoManager;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_main)
    TabLayout tlMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MainFragment.newInstance());
        fragmentList.add(MoreFragment.newInstance());

        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList);
        vpMain.setAdapter(adapter);

        tlMain.setupWithViewPager(vpMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle != null ? bundle.getString(ScanActivity.SCAN_RESULT) : "";

            queryUserInfo(scanResult);
        }
    }

    private void queryUserInfo(String url) {
        showLoading();
        CardInfoManager.getInstance().getUserInfo(url, qvResult -> {
            hideLoading();
            if (qvResult.retSuccess()) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra(EditActivity.INTENT_NAME, qvResult.getObject().getName());
                startActivity(intent);
            } else {
                showMessage("请求失败: " + qvResult.getCode() + " " + qvResult.getErrorInfo());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public TabFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> mFragments) {
            super(fm, behavior);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "扫描二维码";
                case 1:
                    return "更多";
                default:
                    return "";
            }
        }
    }
}
