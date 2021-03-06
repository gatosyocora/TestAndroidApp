/* アプリ起動時最初に表示されるメインのActivity(画面) */
package jp.dmarch.sampleappcation;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/*
写真共有
https://dev.classmethod.jp/smartphone/android/android-tips-35-sharecompat/
フローティングアクションボタン
http://android.keicode.com/basics/ui-floatingactionbutton.php
 */


public class MainActivity extends AppCompatActivity {

    public static final int NEXT_ACTIVITY_ID = 1; // 定数 NextActivityを示すID


    public String sample = "sample"; // 別のクラスからも使える変数(public)
    private String message = "test"; // このクラス内のみで使える変数(private)


    private TextView tv;

    private Gps gps;
    private Bluetooth bt;

    // アプリを起動したとき初めに実行される
    @Override // ←親クラスで存在するメソッドの内容を変更する際につける
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 親クラスのメソッドを実行
        setContentView(R.layout.activity_main); // 画面構成をおこなうlayoutファイル(xml)の指定
        Log.d("call", "onCreate"); // AndroidStudioのログ(Logcat)

        tv = (TextView)findViewById(R.id.textView_main); // 表示用のTextViewの取得

        DBHelper dbHelper = new DBHelper(this); // DBを操作するクラスを宣言

        // データを追加
        dbHelper.createUser("アリス", 15, "女");
        dbHelper.createUser("ボブ", 22, "男");
        dbHelper.createUser("チャーリー", 18, "男");
        dbHelper.createUser("デイブ", 20, "男");

        // GPS
        //gps = new Gps(MainActivity.this);
        //gps.startGps();

        // Bluetooth
        bt = new Bluetooth(MainActivity.this);
        bt.startBluetooth();

        // activity_mainで用意したボタンの使用（Listenerの設定）
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplicationContext(), NextActivity.class); // 切り替え準備
                intent.putExtra("mesId", message); // 送りたいデータを付与
                startActivityForResult(intent, NEXT_ACTIVITY_ID); // Activity切り替え
            }
        });

        // activity_mainで用意したボタンの使用（Listenerの設定）
        Button mapBtn = (Button) findViewById(R.id.button9);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplicationContext(), MapActivity.class); // 切り替え準備
                intent.putExtra("mesId", message); // 送りたいデータを付与
                startActivity(intent); // Activity切り替え
            }
        });


        // activity_mainで用意したボタンの使用（Listenerの設定）
        // 共有ボタン
        Button btnLeft = (Button) findViewById(R.id.button3);
        btnLeft.setText("Share");
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "rereji";

                // 共有の準備
                ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(MainActivity.this);
                builder.setChooserTitle("Choose Send App"); // アプリ選択ダイアログのタイトル
                builder.setSubject("てすと"); // タイトル
                builder.setText("てすと投稿です"); // テキスト
                builder.setType("text/plain"); // データの種類

                // 共有用ダイアログを表示
                builder.startChooser();
            }
        });

        // activity_mainで用意したボタンの使用（Listenerの設定）
        // 現在地取得ボタン
        Button btnCenter = (Button) findViewById(R.id.button4);
        btnCenter.setText("GPS");
        btnCenter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 message = "gato";

                 if (gps.isAvailableGps()) {
                     // 現在地を取得する
                     double gpsData[] = gps.getCurrentLocation();
                     tv.setText("緯度:" + gpsData[0] + "\n経度:" + gpsData[1]);
                 }
             }
        });


        // activity_mainで用意したボタンの使用（Listenerの設定）
        Button btnRight = (Button)findViewById(R.id.button5);
        btnRight.setText("Bluetooth");
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "botti";
                
                ArrayList<String> deviceList = bt.getConnectableDeviceByBluetooth();

                tv.setText(deviceList.toString()); // デバイスリストを表示
            }
        });

    }

    // 別のアクティビティから戻ってきたときに実行される（別Activityからデータを受け取る）
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 特に処理は書いていない
    }

    // アプリを切ったときに実行される
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        bt.finishBluetooth();
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

    // スマホのホームから戻ってきたとき初めに実行される
    // 別のActivityから戻ってきたとき初めに実行される
    @Override
    protected  void onRestart() {
        super.onRestart();
        Log.d("call", "onRestart");
    }
}
