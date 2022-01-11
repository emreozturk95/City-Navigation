package com.example.citynavigation.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.citynavigation.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.fragment_qr_scan.*

class QrScanFragment : Fragment() {

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupControls()
    }

    private fun setupControls() {
        detector = BarcodeDetector.Builder(requireActivity()).setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(requireActivity(), detector)
            .setAutoFocusEnabled(true)
            .build()
        surface_view.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {
        @SuppressLint("MissingPermission")
        override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
            cameraSource.start(surfaceHolder)
        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            cameraSource.stop()
        }

    }

    private val processor = object : Detector.Processor<Barcode> {
        override fun release() {

        }

        override fun receiveDetections(detection: Detector.Detections<Barcode>) {
            val qrCodes: SparseArray<Barcode> = detection.detectedItems
            if (qrCodes.isNotEmpty()) {
                Handler(Looper.getMainLooper()).post {
                    val action =
                        QrScanFragmentDirections.actionQrScanFragmentToHomeFragment(
                            qrCodes.valueAt(
                                0
                            ).displayValue
                        )
                    Navigation.findNavController(requireView()).navigate(action)
                }

            } else {
                Log.e("qrcode", "error")
            }

        }

    }

}