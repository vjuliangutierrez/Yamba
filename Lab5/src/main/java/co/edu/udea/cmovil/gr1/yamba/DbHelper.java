package co.edu.udea.cmovil.gr1.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    // Llamado solo una vez, la primera vez que se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s int primary key, %s text, %s text, %s int)",
                StatusContract.TABLE, StatusContract.Column.ID, StatusContract.Column.USER, StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT);
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    // Es llamado siempre que una version existente != nueva version, i.e. cambio de schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Tipicamente se utiliza ALTER TABLE ...
        db.execSQL("drop table if exists " + StatusContract.TABLE);
        onCreate(db);
    }
}