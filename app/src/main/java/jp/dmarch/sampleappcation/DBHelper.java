/* ローカルDBへ操作を行う */
package jp.dmarch.sampleappcation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "my_database";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "user";


    // DBHelperのコンストラクタ
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION); // DBを作成
    }

    // DBを初めて作成した際に呼ばれる
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 新しいテーブル(userテーブル)を作成
        db.execSQL("create table "+ TABLE_NAME + "("
                + "userid integer primary key autoincrement,"
                + "name text not null,"
                + "age integer,"
                + "gender text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    /* 新しいユーザを作成 (ローカルDBに新規レコード追加)*/
    public void createUser(String name, int age, String gender){

        // DBと接続
        SQLiteDatabase db = this.getWritableDatabase();

        // 追加するデータを作成
        ContentValues insertValues = new ContentValues();
        insertValues.put("name", name);
        insertValues.put("age", age);
        insertValues.put("gender", gender);

        try {
            // データをテーブルに追加
            // insert(テーブル名, null禁止の項目がnullの時に埋めるString, 追加するデータ)
            db.insert(TABLE_NAME, null, insertValues);
        }
        finally {
            db.close(); // DBとの接続を切る
        }
    }
    /* ローカルDBから1人のユーザ情報を取得 */
    public Map<String, String> getUser(int userId){

        Map<String, String> map = new HashMap<>(); // 連想配列(map)を作成
        SQLiteDatabase db = this.getWritableDatabase(); // DBへ接続
        try {
            // 引数のuserIdと同じデータを取得
            Cursor cursor = db.query(TABLE_NAME, new String[]{"userid","name","age","gender"},"userid == ?",new String[]{String.valueOf(userId)},null,null,null);

            cursor.moveToFirst(); // カーソルを一番最初に持ってくる
            // mapにデータを追加する
            map.put("userId", String.valueOf(cursor.getInt(cursor.getColumnIndex("userid"))));
            map.put("name", cursor.getString(cursor.getColumnIndex("name")));
            map.put("age", String.valueOf(cursor.getInt(cursor.getColumnIndex("age"))));
            map.put("gender", cursor.getString(cursor.getColumnIndex("gender")));
            cursor.close(); // 詳しくはわからんけど、使わなくなったからクローズ
        }
        finally {
            db.close(); // DBを切断
        }

        return map;
    }

    /* ローカルDBから全員のユーザ情報を取得 */
    public ArrayList<Map<String, String>> getUsers(){

        ArrayList users = new ArrayList(); // リスト作成
        SQLiteDatabase db = this.getWritableDatabase(); // DBへ接続
        try {
            long recodeCount = DatabaseUtils.queryNumEntries(db, TABLE_NAME); // テーブルのレコード数を取得
            // 1レコード(1人分のユーザ情報)を取り出し, リストに追加
            for (int i = 1; i <= recodeCount; i++) {
                users.add(getUser(i)); //  1人分のユーザ情報を取得
            }
        }
        finally {
            db.close(); // DBを切断
        }

        return users;
    }

    public void deleteTable() {
        Log.d("DBHelper","start deleteTable");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.close();
    }

}
