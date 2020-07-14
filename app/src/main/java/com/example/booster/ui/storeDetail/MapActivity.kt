package com.example.booster.ui.storeDetail

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker

import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : FragmentActivity(), OnMapReadyCallback {
    var markers = ArrayList<MarkerData>()
    var array = mutableListOf<Marker>()
    var university = ""
    lateinit var cameraUpdate: CameraUpdate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        markers = intent.getParcelableArrayListExtra<MarkerData>("marker")
        university = intent.getStringExtra("univ")

        setContentView(R.layout.activity_map)

        btn_back.setOnClickListener {
            markers.clear()
            array.clear()
            finish()
        }

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mainMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mainMap, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    @UiThread
    override fun onMapReady(nMap: NaverMap) {

        val uiSettings = nMap.uiSettings
        uiSettings.isZoomControlEnabled = true
        uiSettings.isLocationButtonEnabled = true

        nMap.locationSource
        nMap.locationTrackingMode
        uiSettings.isScaleBarEnabled = false

        if (university == "숭실대학교"){
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.496575, 126.957427))
        }
        else if (university == "중앙대학교"){
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.505233, 126.957112))
        }
        else{//서울대학교
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.481431, 126.952701))
        }
        act_map_txt_univ.text = university

        nMap.moveCamera(cameraUpdate)
        draw(nMap)

    }

    fun draw(nMap: NaverMap){
        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?:""
            }

        }

        for(i in 0 until markers.size){
            repeat(1000) {
                array.plusAssign(Marker().apply {
                    position = LatLng(markers[i].latitude, markers[i].longitude)
                    icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker_1)
                    tag = markers[i].name
                    width = 50
                    height = 80
                })
            }
        }

        array.forEach { marker ->
            marker.map = nMap
            marker.setOnClickListener {
                for ( i in 0 until array.size){
                    array[i].width = 60
                    array[i].height = 80
                }
                marker.width = 100
                marker.height = 150
                val cameraUpdate = CameraUpdate.scrollTo(marker.position)
                nMap.moveCamera(cameraUpdate)
                infoWindow.open(marker)
                false
            }

        }
    }
}