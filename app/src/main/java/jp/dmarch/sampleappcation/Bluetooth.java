package jp.dmarch.sampleappcation;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;

/**
 * https://techbooster.org/android/application/5191/
 * http://www.kotemaru.org/2013/10/30/android-bluetooth-sample.html
 * https://techbooster.org/android/5535/
 * http://seesaawiki.jp/w/moonlight_aska/d/Bluetooth%A5%C7%A5%D0%A5%A4%A5%B9%A4%F2%C3%B5%A4%B9
 */

public class Bluetooth {

    BluetoothAdapter mBluetoothAdapter;   // アダプタ
    BroadcastReceiver mBroadcastReceiver; // レシーバ
    private ArrayList<String> deviceList; // 接続可能なデバイスリスト

    private final static int REQUEST_ENABLE_BLUETOOTH = 1;

    Context mContext;

    public Bluetooth(Context context) {
        mContext = context;

        startBluetooth();
    }

    // Bluetoothで接続可能なデバイスの検索を始める
    public void startBluetooth() {

        //BluetoothAdapter取得
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Bluetoothが対応していなければ
        if(mBluetoothAdapter == null){
            //Bluetooth非対応端末の場合の処理
            Log.d("Bluetooth","Bluetoothがサポートされていません。");
            return;
        }

        // BluetoothがOFFだった場合、ONにするよう呼びかける
        if(!isAvailableBluetooth()){
            Log.d("Bluetooth","Bluetoothは無効です。");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // 設定呼びかけダイアログの準備
            mContext.startActivity(intent);
        }

        deviceList = new ArrayList<String>(); // 見つけたデバイス名をいれておくデバイス

        // デバイスの検索
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                // デバイスを発見したとき
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // デバイスを取得
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); // 見つかったデバイス
                    String deviceName = device.getName().toString();  // 見つかったデバイスの名前を取得
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE); // 見つかったデバイスの電波強度を取得
                    deviceList.add(deviceName+": RSSI="+String.valueOf(rssi)); // 名前をデバイスリストに追加
                    Log.d("Bluetooth", "find "+deviceName);
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);  // インテントフィルタの作成

        finishBluetooth(); // 検索中なら一度検索を止める

        mContext.registerReceiver(mBroadcastReceiver, filter);  // ブロードキャストレシーバの登録

        mBluetoothAdapter.startDiscovery();  // Bluetoothで接続可能なデバイスを検索開始
    }

    // Bluetooth接続可能なデバイスの検索を止めるメソッド
    public void finishBluetooth() {
        // デバイスの検索中なら
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery(); // 検索をやめる
            mContext.unregisterReceiver(mBroadcastReceiver); // ブロードキャストレシーバを解除
        }
    }

    // Bluetooth機能が使えるか
    public boolean isAvailableBluetooth() {
        return mBluetoothAdapter.isEnabled(); //ONならtrue OFFならfalse
    }

    // Bluetoothで接続可能なデバイスのリストを取得するメソッド
    public ArrayList getConnectableDeviceByBluetooth() {
        return this.deviceList;
    }

    // つかえないらしい
    //@Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent date){
        if(requestCode == REQUEST_ENABLE_BLUETOOTH){
            if(ResultCode == Activity.RESULT_OK){
                //BluetoothがONにされた場合の処理
                Log.d("Bluetooth","BluetoothをONにしてもらえました。");
            }else{
                Log.d("Bluetooth","BluetoothがONにしてもらえませんでした。");
            }
        }
    }
}
