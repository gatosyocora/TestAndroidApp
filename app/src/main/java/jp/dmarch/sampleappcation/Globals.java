/* どのActivityからもアクセスできるグローバル変数を管理するクラス */
package jp.dmarch.sampleappcation;

import android.app.Application;

import java.util.ArrayList;


public class Globals extends Application {

    private ArrayList<UserData> users_list; // ユーザ情報のリスト

    // ユーザのリストを作成するメソッド
    public void createUsers() {

        this.users_list = new ArrayList<UserData>(); // UserDataのオブジェクトを格納するリストを作成

        // 新しいUserDataクラスのオブジェクトを作成（インスタンス化）し、listに追加
        this.users_list.add(new UserData("アリス", 15, "女"));
        this.users_list.add(new UserData("ボブ", 22, "男"));
        this.users_list.add(new UserData("チャーリー", 18, "男"));
        this.users_list.add(new UserData("デイブ", 20, "男"));

    }

    // setter
    public void setUsersData(ArrayList<UserData> data) { this.users_list = data; }

    // getter
    public ArrayList<UserData> getUsersData() { return this.users_list; }

}
