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

    var markers = mutableListOf<MarkerData>()

    var array = mutableListOf<Marker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        btn_back.setOnClickListener {
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

        if(intent.getStringExtra("univ")=="숭실대학교"){
            markers.clear()
            array.clear()
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.496575, 126.957427))
            nMap.moveCamera(cameraUpdate)

            loadDatas1()
            draw(nMap)
        }

        if(intent.getStringExtra("univ")=="중앙대학교") {
            markers.clear()
            array.clear()
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.504210, 126.956788))
            nMap.moveCamera(cameraUpdate)

            loadDatas2()
            draw(nMap)
        }
    }

    fun loadDatas1(){   //얀 숭실대
        markers.apply {
            add(
                MarkerData(
                    latitude = 37.495323,
                    longitude = 126.953295,
                    data = "진흥 인쇄랜드"
                )
            )
            add(
                MarkerData(
                    latitude = 37.495176,
                    longitude = 126.961398,
                    data = "현대문화사"
                )
            )
        }
    }

    fun loadDatas2(){   //얀 중앙대
        markers.apply {
            add(
                MarkerData(
                    latitude = 37.503183,
                    longitude = 126.953737,
                    data = "완유 인쇄사"
                )
            )
            add(
                MarkerData(
                    latitude = 37.503659,
                    longitude = 126.960244,
                    data = "동진인쇄사"
                )
            )
            add(
                MarkerData(
                    latitude = 37.506911,
                    longitude = 126.946038,
                    data = "대원인쇄소"
                )
            )
        }
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
                    icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker_click)
                    tag = markers[i].data
//                    width = 100
//                    height = 100
                })
            }
        }

        array.forEach { marker ->
            marker.map = nMap
            marker.setOnClickListener {
                for ( i in 0 until array.size){
                    array[i].width = 100
                    array[i].height = 100
                }
                marker.width = 150
                marker.height = 150
                val cameraUpdate = CameraUpdate.scrollTo(marker.position)
                nMap.moveCamera(cameraUpdate)
                infoWindow.open(marker)
                false
            }

        }
    }
}