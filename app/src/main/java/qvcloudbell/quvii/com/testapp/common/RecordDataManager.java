package qvcloudbell.quvii.com.testapp.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jxl.write.WritableCellFormat;
import qvcloudbell.quvii.com.testapp.bean.Record;
import qvcloudbell.quvii.com.testapp.database.AppDatabase;
import qvcloudbell.quvii.com.testapp.utils.ExcelUtils;

public class RecordDataManager {

    private RecordDataManager() {
    }

    public static RecordDataManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final RecordDataManager instance = new RecordDataManager();
    }

    //手动导出所有记录
    public void exportAllRecord(SimpleLoadListener listener) {
        List<Record> recordList = AppDatabase.getRecordList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日_HH:mm:ss", Locale.CHINA);
        exportExcel(App.getInstance().getRootPath() + File.separator + "所有记录",
                "记录_" + simpleDateFormat.format(new Date()),
                recordList,
                listener
        );
    }

    public void exportExcel(String path, String fileName, List<Record> recordList, SimpleLoadListener listener) {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            File file = new File(path + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }

            ExcelUtils excelUtils = ExcelUtils.getInstance().create(path, fileName);

            WritableCellFormat titleFormat = new WritableCellFormat();
            WritableCellFormat dataFormat = new WritableCellFormat();

            String[] titles = new String[]{"序号", "姓名", "发放口罩数", "体温记录", "记录时间"};

            Set<String> nameSet = new HashSet<>();
            List<List<String>> listList = new ArrayList<>();

            for (int i = 0; i < recordList.size(); i++) {
                Record record = recordList.get(i);
                if (nameSet.contains(record.getName())) {
                    continue;
                }

                listList.add(generateBean(record));
                for (int j = i + 1; j < recordList.size(); j++) {
                    Record record1 = recordList.get(j);
                    if (record.getName().equals(record1.getName())) {
                        listList.add(generateBean(record1));
                    }
                }
                nameSet.add(record.getName());
            }

            excelUtils.createSheetSetTitle("记录", titles, titleFormat)
                    .fillStringData(listList, dataFormat);
            excelUtils.close();

            e.onNext(Code.SUCCESS);
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        listener.onResult(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onResult(Code.FAIL);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private List<String> generateBean(Record record) {
        List<String> bean = new ArrayList<>();
        bean.add(String.valueOf(record.getId()));
        bean.add(record.getName());
        bean.add(String.valueOf(record.getMask()));
        bean.add(String.valueOf(record.getTemperature()));
        bean.add(record.getDateInfo2());
        return bean;
    }
}
