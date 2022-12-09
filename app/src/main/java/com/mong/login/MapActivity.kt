package com.mong.login

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private var nowLat: Double = 0.0
    private var nowLng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                Toast.makeText(this, "거부됨", Toast.LENGTH_SHORT).show()
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                Toast.makeText(this, "승인됨", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        showUserLocation()
        openUserProfile()
    }

    private fun showUserLocation() {
        naverMap.addOnLocationChangeListener { location ->
            nowLat = location.latitude
            nowLng = location.longitude
            Toast.makeText(this, "${nowLat}, ${nowLng}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUserProfile() {
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.setOnClickListener {
            val profile = ProfileActivity(this)
            profile.show(supportFragmentManager, profile.tag)
            true
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
