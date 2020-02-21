package qvcloudbell.quvii.com.testapp.bean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import qvcloudbell.quvii.com.testapp.database.AppDatabase;

@Table(database = AppDatabase.class)
public class Record extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private Long id;
    @Column
    private String name;
    @Column
    private int mask;
    @Column
    private double temperature;
    @Column
    private long time;

    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormat2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDateInfo() {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss", Locale.CHINA);
        }

        return simpleDateFormat.format(new Date(time));
    }

    public String getDateInfo2() {
        if (simpleDateFormat2 == null) {
            simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }

        return simpleDateFormat2.format(new Date(time));
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mask=" + mask +
                ", temperature=" + temperature +
                ", time=" + time +
                '}';
    }
}
