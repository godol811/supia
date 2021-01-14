package com.example.supia.Activities.Calendar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class myDBHelper extends SQLiteOpenHelper {
    public static String TAG = "데이터베이스핼퍼_SQLite";

    private static String DB_PATH = "";
    public static final String DB_NAME = "supia.db";
    public static final String T_1 = "supiamensterm";
    public static final String T1_COL_1 = "mStart";
    public static final String T1_COL_2 = "mEnd";

    private SQLiteDatabase mDataBase;

    private final Context mContext;

    public myDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT>=17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data" + context.getPackageName() + "/database/";
        }
        this.mContext = context;
    }

    public void createDataBase() throws IOException
    {
        //데이터베이스가 없으면 asset폴더에서 복사해온다.
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    ///data/data/your package/databases/Da Name <-이 경로에서 데이터베이스가 존재하는지 확인한다
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //assets폴더에서 데이터베이스를 복사한다.
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //데이터베이스를 열어서 쿼리를 쓸수있게만든다.
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

//    void DBSearch(String tableName) {
//        Cursor cursor = null;
//
//        try {
//            cursor = db.query(tableName, null, null, null, null, null, null);
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    String id = cursor.getString(cursor.getColumnIndex("ID"));
//                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
//                    String age = cursor.getString(cursor.getColumnIndex("AGE"));
//                    String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
//
//                    Log.d(TAG, "id: " + id + ", name: " + name + ", age: " + age + ", phone: " + phone);
//                }
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
//
//    // SELECT * FROM People WHERE age < "age" ORDER BY NAME
//    void DBSearch(String tableName, Integer age) {
//        Cursor cursor = null;
//
//        try {
//            cursor = db.query(tableName, null, "AGE" + " < ?", new String[]{age.toString()}, null, null, "NAME");
//
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    String id = cursor.getString(cursor.getColumnIndex("ID"));
//                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
//                    String age2 = cursor.getString(cursor.getColumnIndex("AGE"));
//                    String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
//
//                    Log.d(TAG, "id: " + id + ", name: " + name + ", age: " + age2 + ", phone: " + phone);
//                }
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
//
//    public Cursor getAllDates() {
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery("select * from " + T_1, null);
//        database.close();
//        return cursor;
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE supiamensterm (mStart DATE(20), mEnd DATE(20));");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS supiamensterm");
//        onCreate(db);
//
//    }
//    public void displayUsers() {
//        Cursor cursor = myDBHelper.getAllDates(); //here's where the error keeps on happening
//        if(cursor.getCount() == 0) {
//            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
