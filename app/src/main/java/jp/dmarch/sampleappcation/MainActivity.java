/* アプリ起動時最初に表示されるメインのActivity(画面) */
package jp.dmarch.sampleappcation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public static final int NEXT_ACTIVITY_ID = 1; // 定数 NextActivityを示すID

    public String sample = "sample"; // 別のクラスからも使える変数(public)
    private String message = "test"; // このクラス内のみで使える変数(private)


    // アプリを起動したとき初めに実行される
    @Override // ←親クラスで存在するメソッドの内容を変更する際につける
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 親クラスのメソッドを実行
        setContentView(R.layout.activity_main); // 画面構成をおこなうlayoutファイル(xml)の指定
        Log.d("call","onCreate"); // AndroidStudioのログ(Logcat)

        //new DBHelper(this).deleteTable();

        // activity_mainで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplicationContext(), NextActivity.class); // 切り替え準備
                intent.putExtra("mesId", message); // 送りたいデータを付与
                startActivityForResult(intent, NEXT_ACTIVITY_ID); // Activity切り替え
            }
        });


        // NextActivityに渡すテキストデータの選択
        // activity_mainで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { message = "rereji"; }
        });

        // activity_mainで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { message = "gato"; }
        });

        // activity_mainで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { message = "botti"; }
        });

    }

    // 別のアクティビティから戻ってきたときに実行される（別Activityからデータを受け取る）
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 特に処理は書いていない
    }

    //**********************ここから下は主な処理なし（ライフサイクル検証のため書いただけ）***********************

    // アプリを起動したときonCreateの次に実行される
    // スマホのホームから戻ってきたときonRestartの次に実行される
    // 別のActivityから戻ってきたときonRestartの次に実行される
    @Override
    protected  void onStart() {
        super.onStart();
        Log.d("call","onStart");
    }

    // アプリを起動したときonStartの次に実行される
    // スマホのホームから戻ってきたときonStartの次に実行される
    // 別のActivityから戻ってきたときにonStartの次に実行される
    @Override
    protected  void onResume() {
        super.onResume();
        Log.d("call","onResume");
    }

    // スマホのホームへ行くとき初めに実行される
    // 別のActivityに行くとき初めに実行される
    @Override
    protected  void onPause() {
        super.onPause();
        Log.d("call","onPause");
    }

    //　スマホのホームに行くときにonPauseの次に実行される
    // 別のActivityに行くときonPauseの次に実行される
    @Override
    protected  void onStop() {
        super.onStop();
        Log.d("call","onStop");
    }

    // アプリを切ったときに実行される
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        Log.d("call","onDestroy");
    }

    // スマホのホームから戻ってきたとき初めに実行される
    // 別のActivityから戻ってきたとき初めに実行される
    @Override
    protected  void onRestart() {
        super.onRestart();
        Log.d("call","onRestart");
    }
}
