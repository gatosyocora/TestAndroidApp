/* リストで性別に色を付けるために使うViewBinder */
/*
 どうやらリストを構成するViewすべて(TextViewとかButtonとか)に
　このプログラムが一度適用されるっぽい
*/
package jp.dmarch.sampleappcation;

import android.graphics.Color;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ColorFrameViewBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object data, String s) {
        // このViewが性別を表示するViewかどうか
        if (view.getId() == R.id.textView6) {
            TextView tv = (TextView) view; // ViewをTextViewに変換
            // genderが男なら青色, 女なら赤色に設定
            if (s.equals("男")) {
                tv.setTextColor(Color.BLUE);
            } else {
                tv.setTextColor(Color.RED);
            }
            tv.setText(s); // 男or女を表示
            return true;
        }
        return false;
    }
}
