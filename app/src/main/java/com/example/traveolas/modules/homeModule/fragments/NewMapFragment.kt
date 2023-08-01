package com.example.traveolas.modules.homeModule.fragments

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.example.traveolas.R
import com.example.traveolas.databinding.FragmentNewMapBinding
import com.example.traveolas.modules.homeModule.viewModel.MapViewModel
import com.example.traveolas.utils.LocationHelper
import com.example.traveolas.utils.Utils
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMapFragment : Fragment() {

    private lateinit var binding: FragmentNewMapBinding
    private val viewModel by viewModels<MapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initLocationComponent()
        initView()
        initLocation()
    }

    private fun observeData() {
        viewModel.location.observe(viewLifecycleOwner) {
            it?.let { it1 -> setCameraOnCurrentLocation(it1) }
        }
    }

    private fun initLocation() {

    }

    private fun initView() {
        binding.apply {
            tvRecenter.setOnClickListener {
                LocationHelper(requireContext()).location?.let {
                    setCameraOnCurrentLocation(it)
                }
            }
        }
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = binding.mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_navigation,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCameraOnCurrentLocation(
        location: Location,
        mZoom: Double = 14.0,
        mPitch: Double = 0.0,
        mBearing: Double = -45.0
    ) {
        binding.tvCoordinates.text = "${
            Utils.convertLatLongToDMS(
                location.latitude,
                location.longitude
            )
        } : ${location.altitude}"
        binding.mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            binding.mapView.camera.apply {
                val center = createCenterAnimator(
                    CameraAnimatorOptions.cameraAnimatorOptions(
                        Point.fromLngLat(
//                            longitude,
//                            latitude
                            location.longitude,
                            location.latitude
                        )
                    ) {
                    }
                )
                val zoom = createZoomAnimator(
                    CameraAnimatorOptions.cameraAnimatorOptions(mZoom) {
//                        startValue(currentZoom - mZoom)
                    }
                ) {
                    duration = 2000
                    interpolator = AccelerateDecelerateInterpolator()
                }
                val bearing = createBearingAnimator(
                    CameraAnimatorOptions.cameraAnimatorOptions(
                        mBearing
                    )
                ) {
                    duration = 2000
                    interpolator = AccelerateDecelerateInterpolator()
                }
                val pitch = createPitchAnimator(
                    CameraAnimatorOptions.cameraAnimatorOptions(0.0) {
                        startValue(mPitch)
                    }
                ) {
                    duration = 2000
                    interpolator = AccelerateDecelerateInterpolator()
                }
                playAnimatorsTogether(bearing, pitch, center, zoom)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewMapFragment()
    }
}