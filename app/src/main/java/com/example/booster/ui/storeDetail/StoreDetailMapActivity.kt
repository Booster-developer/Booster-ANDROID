package com.example.booster.ui.storeDetail

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.example.booster.listener.onlyOneClickListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_store_detail_map.*

class StoreDetailMapActivity : AppCompatActivity(), OnMapReadyCallback {
    var markers = ArrayList<MarkerData>()

    var x = 0.0
    var y = 0.0
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail_map)

        btn_back.onlyOneClickListener {
            finish()
        }
        markers = intent.getParcelableArrayListExtra<MarkerData>("marker")

        act_store_detail_map_txt_univ.text = markers[0].name
        x = markers[0].latitude!!.toDouble()
        y = markers[0].longitude!!.toDouble()

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.detailMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.detailMap, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(nMap: NaverMap) {

        val uiSettings = nMap.uiSettings
        uiSettings.isScaleBarEnabled = false

        var cameraUpdate = CameraUpdate.scrollTo(LatLng(x, y))
        nMap.moveCamera(cameraUpdate)
        draw(nMap)

    }

    fun draw(nMap: NaverMap){
        val infoWindow2 = InfoWindow()
        val marker = Marker()
        marker.position = LatLng(x,y)
        marker.icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker_click)
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO
        marker.map = nMap
        marker.tag = markers[0].name

        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow2.marker?.tag as CharSequence? ?: name
            }
        }

        infoWindow2.position = marker.position
        infoWindow2.open(marker)

    }
}
