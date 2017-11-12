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

public class UserListActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_userlist);

        ListView list = (ListView) findViewById(R.id.listView); // ユーザのリストを表示するListViewを紐づけ

        dbHelper = new DBHelper(this); // DBを操作するクラスを宣言

        // データを追加
        dbHelper.createUser("アリス",15,"女");
        dbHelper.createUser("ボブ", 22, "男");
        dbHelper.createUser("チャーリー", 18, "男");
        dbHelper.createUser("デイブ", 20, "男");

        // ListViewにArrayListの内容を表示するためAdapterを設定
        // SimpleAdapter(わからん, ArrayList<Map<String, String>>, リストの1要素を設計するlayoutファイル, 表示する情報のキー, 表示する情報を格納するView)
        SimpleAdapter adapter = new SimpleAdapter(this, dbHelper.getUsers(), R.layout.item,
                new String[]{ "name", "age", "gender" }, new int[] { R.id.textView4, R.id.textView5, R.id.textView6});

        adapter.setViewBinder(new ColorFrameViewBinder()); // 性別の文字色を変えるために設定
        // ↑ 写真の表示や枠線の色を動的に変化させる場合に必要

        list.setAdapter(adapter); // ListViewにAdapterを設定

        // アイテムクリック時ののイベントを追加
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                // アイテム選択時
                // 選択アイテムのユーザの名前を取得
                TextView tv = (TextView)view.findViewById(R.id.textView4);
                String value = tv.getText().toString();

                Log.d("selectList",value);
            }
        });


    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.deleteTable();
    }*/
}
