/* ユーザ情報を管理するクラス */
package jp.dmarch.sampleappcation;

public class UserData {
    private String name; // 名前
    private int age; // 年齢
    private boolean gender; // 性別（男→true, 女→false）

    // UserDataクラスのコンストラクタ
    public UserData(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = (gender == "男");
    }

    // nameのgetter
    public String getName() {
        return this.name;
    }

    // ageのgetter
    public int getAge() {
        return this.age;
    }

    // genderのgetter
    // genderがtrueなら男, falseなら女を返す
    public String getGender() {
        return gender ? "男" : "女";
    }
}
