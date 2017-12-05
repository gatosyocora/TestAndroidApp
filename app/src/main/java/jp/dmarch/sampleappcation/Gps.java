package jp.dmarch.sampleappcation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


/*
 * GPS
 * https://qiita.com/yasumodev/items/5f0f030f0ebfcecdff11
 * http://www.adakoda.com/android/000125.html
 * https://akira-watson.com/android/gps.html
 * https://akira-watson.com/android/gps-permission.html
 * http://ria10.hatenablog.com/entry/20121109/1352470160
 * http://mslgt.hatenablog.com/entry/2015/12/29/004133
 */

public class Gps implements LocationListener {

    private double gpsData[] = {0.0, 0.0};
    public LocationManager mLocationManager;
    private Context mContext;

    private final int REQUEST_PERMISSION = 1000; // なんかGPS機能の使用許可に使うらしい

    public Gps(Context context) {
        mContext = context;
        startGps();
    }

    // GPSの監視を開始するメソッド
    public void startGps() {
        // GPSを使うために設定
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // アプリでGPSの使用が許可されていないなら
        // Android6.0からはパーミッションの確認がないとGPS機能が使えなくなった(Manifestにも使うことを明記する必要あり）
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // GPS機能の利用許可を要求
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_PERMISSION);
        }


        if (isAvailableGps()) {
            Log.d("GPS", "start GPS");
            // 位置情報の更新を開始
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // 使う機能(GPS機能かWifi機能か) // NETWORK_PROVIDER
                    1000, // 通知のための最小時間間隔（ミリ秒）
                    1, // 通知のための最小距離間隔（メートル）
                    this // リスナー
            );
        } else {
            enableGpsSetting();
        }
    }

    //　GPSの監視を終了させるメソッド
    public void finishGps() {
        // 位置情報の更新の取得を終了
        mLocationManager.removeUpdates(this);
    }

    // 現在地を返すメソッド
    public double[] getCurrentLocation() {
        if (!isAvailableGps()) enableGpsSetting();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return this.gpsData;
        }
        // もしGPSでデータを取得できていないならWIFIで計測した最新データを表示
        if (this.gpsData[0] == 0.0 && this.gpsData[1] == 0.0) {
            Location local = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            this.gpsData[0] = local.getLatitude();
            this.gpsData[1] = local.getLongitude();
        }
        return this.gpsData;
    }

    // GPSの設定画面を表示するメソッド
    public void enableGpsSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(intent);
    }

    // 現在地が更新された時に呼ばれるメソッド
    @Override
    public void onLocationChanged(Location location) {
        gpsData[0] = location.getLatitude();
        gpsData[1] = location.getLongitude();
        String msg = "Lat=" + gpsData[0]    // 緯度取得
                + "\nLng=" + gpsData[1];   // 経度取得
        Log.d("GPS", msg);
    }

    // GPS機能が使えるかどうか
    public boolean isAvailableGps(){
        boolean gpsFlag = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpsFlag;
    }

    /* これつかえないらしい */
    // GPS機能の利用許可ダイアログで選択したあとに実行されるメソッド
    //@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 設定画面へ飛ばす
                enableGpsSetting();
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
