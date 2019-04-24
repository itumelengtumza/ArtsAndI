package za.co.bubiit.ArtsAndI.helper_util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    /* renamed from: A */
    private String[] f16A;
    private int numElements = 0;

    public SQLiteHandler(Context context, String[] A) {
        super(context, A[2], null, 5);
        this.f16A = A;
    }

    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + this.f16A[3] + "(" + this.f16A[4] + " TEXT";
        for (int i = 5; i < this.f16A.length; i++) {
            createTable = createTable + "," + this.f16A[i] + " TEXT";
        }
        createTable = createTable + ")";
        db.execSQL(createTable);
        Log.d(TAG, "SQLite DB tables created: " + createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.f16A[3]);
        onCreate(db);
    }

    public void addToSQLiteDB(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(this.f16A[3], null, values);
        db.close();
        Log.d(TAG, "New " + this.f16A[3] + " item inserted into sqlite: " + id);
    }

    public ArrayList<String[]> getFromSQLiteDB(String clause) {
        ArrayList<String[]> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + this.f16A[3] + clause, null);
        cursor.moveToFirst();
        this.numElements = cursor.getCount();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                String[] temp = new String[cursor.getColumnCount()];
                for (int i = 1; i < cursor.getColumnCount(); i++) {
                    temp[i] = cursor.getString(i);
                }
                list.add(temp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getNumElements() {
        return this.numElements;
    }

    public void deleteSQLiteData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(this.f16A[3], null, null);
        db.close();
        Log.d(TAG, "Deleted all " + this.f16A[3] + " info from sqlite");
    }
}
