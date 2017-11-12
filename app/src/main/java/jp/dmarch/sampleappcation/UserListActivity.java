/* ユーザのリストを表示するActivity(画面) */
package jp.dmarch.sampleappcation;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Map<String, String>> usersList;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_userlist);

        ListView list = (ListView) findViewById(R.id.listView); // ユーザのリストを表示するListViewを紐づけ

        dbHelper = new DBHelper(this); // DBを操作するクラスを宣言

        usersList = dbHelper.getUsers(); // ユーザ情報のリストを取得

        // ListViewにArrayListの内容を表示するためAdapterを設定
        // SimpleAdapter(わからん, ArrayList<Map<String, String>>, リストの1要素を設計するlayoutファイル, 表示する情報のキー, 表示する情報を格納するView)
        SimpleAdapter adapter = new SimpleAdapter(this, usersList, R.layout.item,
                new String[]{ "name", "age", "gender" }, new int[] { R.id.textView4, R.id.textView5, R.id.textView6});

        adapter.setViewBinder(new ColorFrameViewBinder()); // 性別の文字色を変えるために設定
        // ↑ 写真の表示や枠線の色を動的に変化させる場合に必要

        list.setAdapter(adapter); // ListViewにAdapterを設定

        // アイテムクリック時ののイベントを追加
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                // アイテム選択時
                // 選択アイテムのuseridを取得
                Map<String, String> user = usersList.get(pos);
                String userid = user.get("userid");

                Log.d("selectList",userid);
            }
        });


    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.deleteDataOfTable();
    }*/
}
