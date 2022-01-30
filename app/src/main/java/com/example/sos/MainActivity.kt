package com.example.sos

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sos.Utils.showSnackbar
import com.example.sos.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var layout: View
    private lateinit var camera: CameraView
    private lateinit var checkLocationPermission: ActivityResultLauncher<Array<String>>
    private var permissionsRequired = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layout = binding.mainLayout
        camera = binding.camera
        camera.setLifecycleOwner(this)
        takePhoto()

//        checkLocationPermission = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
//                permissions[Manifest.permission.CAMERA] == true) {
//                layout.showSnackbar(
//                    layout,
//                    getString(R.string.permission_granted),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) {
//                    onClickRequestPermission()
//
//                }
//            } else {
//                // Permission was denied. Display an error message.
//            }
//        }

    }

//    private ActivityResultLauncher<String> requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),isGranted
//        ) {
//            if (it.){
//
//            }
//
//        }

    fun onClickRequestPermission() {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                layout.showSnackbar(
                    layout,
                    getString(R.string.permission_granted),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.ok)
                ) {
                    takePhoto()
                }
            } else {
                checkLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA))
            }

//        when {
//            ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                layout.showSnackbar(
//                    layout,
//                    getString(R.string.permission_granted),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) {
//                    takePhoto()
//                }
//            }
//
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.CAMERA
//            ) -> {
//                layout.showSnackbar(
//                    layout,
//                    getString(R.string.permission_required),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) {
//                    requestPermissionLauncher.launch(
//                        Manifest.permission.CAMERA
//                    )
//                }
//            }
//
//            else -> {
//              // requestPermission(arrayOf(Manifest.permission.CAMERA,Manifest.permission.))
//            }
//        }
    }


    private fun takePhoto() {
        binding.takePicture.setOnClickListener {
            camera.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    Log.i("hgrgg",result.data.toString())
                    // A Picture was taken!
                }
            })
            camera.takePicture()
        }


    }
}