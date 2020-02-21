package qvcloudbell.quvii.com.testapp.database;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import qvcloudbell.quvii.com.testapp.bean.Record;
import qvcloudbell.quvii.com.testapp.bean.Record_Table;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "database";

    public static final int VERSION = 1;

    public static Record getRecord(Long id) {
        return SQLite.select().from(Record.class).where(Record_Table.id.eq(id)).querySingle();
    }

    public static List<Record> getRecordList() {
        return SQLite.select().from(Record.class).orderBy(Record_Table.time, false).queryList();
    }

    public static List<Record> getRecordListWithName(String name) {
        return SQLite.select().from(Record.class).where(Record_Table.name.eq(name)).queryList();
    }

    public static void clearRecord() {
        SQLite.delete(Record.class).execute();
    }
}
