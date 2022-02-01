package com.example.sos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sos.Repository.SosRepository
import com.example.sos.Utils.ViewModelFactory

import com.example.sos.UtilsFile.Alert
import com.example.sos.UtilsFile.showSnackbar
import com.example.sos.ViewModel.MainViewModel
import com.example.sos.databinding.ActivityMainBinding
import com.example.sos.model.SosInfoRequest
import com.example.sos.network.ApiService
import com.example.sos.network.ResponseManager
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var layout: View
    private lateinit var camera: CameraView
    private val retrofitService = ApiService.getInstance()
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(SosRepository(retrofitService)))
            .get(MainViewModel::class.java)
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    var placeLat: String? = null
    var placeCordinate: List<String>? = null
    var placeLong: String? = null
    var imageString: String? = null
    private var currentLocation: Location? = null
    //pre configured phone numbers
    var phoneNumber = arrayOf("09015650808", "09012345578")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.mainLayout
        camera = binding.camera
        camera.setLifecycleOwner(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        takePhoto()
    }


    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun takePhoto() {
        binding.takePicture.setOnClickListener {
            camera.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    imageString = Base64.encodeToString(result.data, 0)
                    permission()
                }
            })
            camera.takePicture()
        }
    }


    private fun permissionAcesss() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION),
            -> {
                if (isLocationEnabled()) {
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                        var location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData()
                            if (fusedLocationProviderClient != null) {
                                fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                            }
                        } else {
                            placeLat = location.latitude.toString()
                            placeLong = location.longitude.toString()
                            placeCordinate = listOf<String>(placeLong!!, placeLat!!)
                            sosRequest(imageString!!)
                        }
                    }
                } else {
                    this@MainActivity.Alert()
                }
            }
            else -> {
                permission()
            }
        }
    }

    private fun permission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.i("uuu", "permission required")
            layout.showSnackbar(
                layout,
                "permission  is required to access your location",
                Snackbar.LENGTH_INDEFINITE,
                "ok"
            ) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("uuu", "permission granted")
                permissionAcesss()
            } else {
                Log.i("uuu", "permission denied")
                permission()
            }
        }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            mLocationCallback,
            Looper.myLooper())
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            currentLocation = locationResult.lastLocation

        }
    }

    private fun sosRequest(imageString: String) {
        val request = SosInfoRequest(phoneNumber.toList(), imageString,
            placeCordinate)
        makeSosRequest(request)
    }


    private fun makeSosRequest(sosInfoRequest: SosInfoRequest) {
        viewModel?.sendSosRequest(sosInfoRequest)?.observe(this) { response ->
            when (response) {
                is ResponseManager.Failure -> {
                    Toast.makeText(applicationContext,"Request Failed", Toast.LENGTH_LONG).show()
                }
                is ResponseManager.Loading -> {

                }
                is ResponseManager.Success -> {
                    Toast.makeText(applicationContext,"Request Successful", Toast.LENGTH_LONG).show()

                }

            }
        }

    }
}