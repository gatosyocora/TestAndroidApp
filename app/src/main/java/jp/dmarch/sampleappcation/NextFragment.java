/* 画面下からコンテンツが表示されるFragmentクラス（作成中） */
package jp.dmarch.sampleappcation;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * https://qiita.com/Reyurnible/items/dffd70144da213e1208b
 * http://symfoware.blog68.fc2.com/blog-entry-2058.html
 */

public class NextFragment extends Fragment {

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_next, container, false);
    }*/

    // Viewが生成し終わったときに実行されるメソッド
    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

    }
}
