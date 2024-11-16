package com.example.yandexmaps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.yandexmaps.databinding.ActivityMainBinding
import com.yandex.mapkit.*
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.*
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.ui_view.ViewProvider

open class MainActivity : AppCompatActivity(), DrivingSession.DrivingRouteListener,
    UserLocationObjectListener, CameraListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var checkLocationPermission: ActivityResultLauncher<Array<String>>
    private lateinit var userLocationLayer: UserLocationLayer
    private var routeStartLocation = Point(55.755811, 37.617617)
    private var permissionLocation = false
    private var followUserLocation = false
    private var isOpened = false
    private lateinit var buttonFive: Button
    private lateinit var mapView: MapView
    private var ROUTE_START_LOCATION = Point(55.822363, 37.319288)
    private val ROUTE_END_LOCATION = Point(55.703256, 37.528967)
    private var mapObjects: MapObjectCollection? = null
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession: DrivingSession? = null
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("0dfc6451-cdf9-46b5-979b-a3b406ff907c")
        MapKitFactory.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapview

        mapView.map.move(
            CameraPosition(ROUTE_START_LOCATION, 15f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 6f),
            null
        )
        val view = View(this)
        view.background = applicationContext.getDrawable(R.drawable.invalid)
        mapView.map.mapObjects.addPlacemark(ROUTE_START_LOCATION, ViewProvider(view))
        view.background = applicationContext.getDrawable(R.drawable.baseline_close_24)
        mapView.map.mapObjects.addPlacemark(ROUTE_END_LOCATION, ViewProvider(view))
        val mapkit: MapKit = MapKitFactory.getInstance()
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()

        buttonFive = findViewById(R.id.buttonFive)
        checkLocationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                onMapReady()
            }
        }
        buttonFive.setOnClickListener {
            counter++
            if (counter == 1){
                if (isOpened) {
                    return@setOnClickListener
                }
                clearMapObjects()
                submitRequest2()
                isOpened = true
                checkPermission()
                userInterface()
            }
            else{
                clearMapObjects()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onMapReady()
        } else {
            checkLocationPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun userInterface() {
        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        binding.mapview.map.logo.setAlignment(mapLogoAlignment)
    }

    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)

        binding.mapview.map.addCameraListener(this)

        binding.mapview.map.move(
            CameraPosition(routeStartLocation, 16f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )

        cameraUserPosition()
        permissionLocation = true
    }

    private fun cameraUserPosition() {
        if (userLocationLayer.cameraPosition() != null) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            if (followUserLocation) {
                binding.mapview.map.move(
                    CameraPosition(routeStartLocation, 16f, 0f, 0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }
        } else {
            binding.mapview.map.move(
                CameraPosition(Point(55.755811, 37.617617), 16f, 0f, 0f)
            )
        }
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {

    }

    private fun setAnchor() {
        if (userLocationLayer.cameraPosition() != null) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            if (followUserLocation) {
                mapView.map.move(
                    CameraPosition(routeStartLocation, 16f, 0f, 0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }
        } else {
            mapView.map.move(
                CameraPosition(routeStartLocation, 16f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }

    override fun onObjectAdded(p0: UserLocationView) {
        setAnchor()
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
        submitRequest2()
        cameraUserPosition()
        setAnchor()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapView.onStart()
        submitRequest()

        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        for (route in p0) {
            mapObjects!!.addPolyline(route.geometry)
        }
    }

    override fun onDrivingRoutesError(p0: Error) {
        Toast.makeText(this, "Буээээ", Toast.LENGTH_SHORT).show()
    }

    private fun submitRequest() {
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        val requestPoints: ArrayList<RequestPoint> = ArrayList()
        requestPoints.add(
            RequestPoint(
                ROUTE_START_LOCATION,
                RequestPointType.WAYPOINT,
                null,
                null
            )
        )
        requestPoints.add(
            RequestPoint(
                ROUTE_END_LOCATION,
                RequestPointType.WAYPOINT,
                null,
                null
            )
        )

        drivingSession = drivingRouter!!.requestRoutes(
            requestPoints,
            drivingOptions,
            vehicleOptions,
            this
        )
    }

    private fun submitRequest2(){
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        val requestPoints: ArrayList<RequestPoint> = ArrayList()
        if (routeStartLocation != Point(55.755811, 37.617617)){
            requestPoints.add(
                RequestPoint(
                    routeStartLocation,
                    RequestPointType.WAYPOINT,
                    null,
                    null
                )
            )
            requestPoints.add(
                RequestPoint(
                    ROUTE_END_LOCATION,
                    RequestPointType.WAYPOINT,
                    null,
                    null
                )
            )}

        drivingSession = drivingRouter!!.requestRoutes(
            requestPoints,
            drivingOptions,
            vehicleOptions,
            this
        )
    }


    private fun clearMapObjects() {
        mapObjects?.clear()
        submitRequest2()
    }
}
