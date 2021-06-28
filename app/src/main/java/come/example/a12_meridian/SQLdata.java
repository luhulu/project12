package come.example.a12_meridian;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLdata extends SQLiteOpenHelper {
    public SQLdata(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS rememberpassword(account CHAR(10),password CHAR(10));");
        db.execSQL("CREATE TABLE IF NOT EXISTS standard(data INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS account(accountdata CHAR(10),password CHAR(10));");
        db.execSQL("CREATE TABLE IF NOT EXISTS standard(data INT);");
        db.execSQL("INSERT INTO standard VALUES(100);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
