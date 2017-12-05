package jp.dmarch.sampleappcation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Queue;

/**
 * https://qiita.com/rkonno/items/8bec3d5a45235fc88a08
 * https://akira-watson.com/android/google-maps-api-v2.html
 * https://cfm-art.sakura.ne.jp/sys/archives/616
 * マーカのカスタマイズ
 * http://seesaawiki.jp/w/moonlight_aska/d/%A5%DE%A1%BC%A5%AB%A1%BC%A4%F2%A5%AB%A5%B9%A5%BF%A5%DE%A5%A4%A5%BA%A4%B9%A4%EB
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    GoogleMap mMap;
    MapFragment mapFragment;
    Gps mGps; // GPS機能を使うためのクラス
    Marker mMarker; // 現在配置されているマーカ

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        mapFragment = MapFragment.newInstance();

        FragmentManager mFragmentManager = getFragmentManager(); // 管理オブジェクト取得
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction(); // フラグメント管理を開始
        mFragmentTransaction.add(android.R.id.content, mapFragment); // フラグメントを追加
        mFragmentTransaction.commit(); // フラグメントを反映

        mapFragment.getMapAsync( this );
    }


    /* 初期化検知 */
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151); // シドニーの座標

        mMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("シドニー")); // マーカを追加
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); // カメラをシドニーの座標に移動

        mGps = new Gps(this);

        // 各種コールバック
        googleMap.setOnMapLongClickListener( this );
    }

    /** 長押し検知 */
    @Override
    public void onMapLongClick( LatLng latLng ) {
        // 長押しした座標をログに表示
        Log.d("Map","long touch:"+latLng.latitude + "," + latLng.longitude );
        setCurrentLocation(); // 現在地にピンを立て移動
    }

    // 現在地にピンを立てる
    public void setCurrentLocation() {
        mMarker.remove(); // 現在配置しているマーカを削除

        double currentLocation[] = mGps.getCurrentLocation();
        LatLng currentLocat = new LatLng(currentLocation[0], currentLocation[1]); // 現在地の座標
        mMarker = mMap.addMarker(new MarkerOptions().position(currentLocat).title("現在地")); // 新しいマーカを設定
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocat)); // カメラを現在地の座標に移動

        CameraUpdate cUpdate = CameraUpdateFactory.newLatLngZoom( currentLocat, 16); // カメラの移動先と拡大率(数値大→より拡大)を設定
        mMap.moveCamera(cUpdate); // カメラを反映
    }
}
