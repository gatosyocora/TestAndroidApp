/* MainActivityから遷移した先のActivity(画面) */
package jp.dmarch.sampleappcation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class NextActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // 前アクティビティからのデータの受け取り
        Intent intent = getIntent();
        String mes = intent.getStringExtra("mesId"); // mesIdを基に取得

        // activity_nextで用意したEditTextを取得
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText(mes); // 受け取ったテキストデータを表示

        // activity_nextで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理

                // 前のActivityへ返信するデータを設定
                Intent intent = new Intent();
                intent.putExtra("responceId","Success!");
                setResult(RESULT_OK,intent);

                finish(); // このActivityを終了し, 前のActivity(MainActivity)へ戻る

            }
        });

        // activity_nextで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplicationContext(), UserListActivity.class); // 切り替え準備
                startActivity(intent); // Activity切り替え

            }
        });

        // activity_nextで用意したボタンの使用（Listenerの設定）
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplicationContext(), TabActivity.class); // 切り替え準備
                startActivity(intent); // Activity切り替え

            }
        });

    }
}
