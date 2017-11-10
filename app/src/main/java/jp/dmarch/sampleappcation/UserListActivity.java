/* ユーザのリストを表示するActivity(画面) */
package jp.dmarch.sampleappcation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_userlist);

        ListView list = (ListView) findViewById(R.id.listView); // ユーザのリストを表示するListViewを紐づけ

        Globals globals = (Globals)this.getApplication(); // ユーザのリストを扱うために宣言

        // ListViewにArrayListの内容を表示するためAdapterを設定
        // SimpleAdapter(わからん, ArrayList<Map<String, String>>, リストの1要素を設計するlayoutファイル, 表示する情報のキー, 表示する情報を格納するView)
        SimpleAdapter adapter = new SimpleAdapter(this, getMapList(globals.getUsersData()), R.layout.item,
                new String[]{ "name", "age", "gender" }, new int[] { R.id.textView4, R.id.textView5, R.id.textView6});
        adapter.setViewBinder(new ColorFrameViewBinder()); // 性別の文字色を変えるために設定
        // ↑ 写真の表示や枠線の色を動的に変化させる場合に必要
        list.setAdapter(adapter); // ListViewにAdapterを設定

    }

    // ユーザのリスト(ArrayList<UserData>)をSimpleAdapterを使ってListViewに表示するために
    // ArrayList<Map<String, String>>の形式に変換
    public ArrayList<Map<String, String>> getMapList(ArrayList<UserData> users) {
        // Map<String, String>を格納するリストを作成
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        // ArrayList<UserData>からArrayList<Map<String, String>>形式へ
        // ユーザ1人分の情報ごとに処理をおこなっていく
        for (int i = 0; i < users.size(); i++) {
            Map map = new HashMap<String, String>(); // Map<String,String>を作成
            map.put("name", users.get(i).getName()); // 名前
            map.put("age", Integer.toString(users.get(i).getAge())); // 年齢
            map.put("gender", users.get(i).getGender()); // 性別
            list.add(map); // リストに追加
        }
        return list;
    }
}
