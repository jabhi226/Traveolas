package com.example.traveolas.homeModule.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Path
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.traveolas.R
import com.example.traveolas.databinding.FragmentMapBinding
import com.example.traveolas.utils.SharedPref
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.LineBuilder
import org.osmdroid.util.PathBuilder
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline


class MapFragment : Fragment() {

    companion object {
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 101
    }

    private var binding: FragmentMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        askPermissions()
        initMap()
        initView()
    }

    private fun askPermissions() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET
        )
        requestPermissionsIfNecessary(permissions)
    }

//    private val listOfTile = arrayOf<ITileSource>(
//        TileSourceFactory.MAPNIK,
//        TileSourceFactory.WIKIMEDIA,
//        TileSourceFactory.PUBLIC_TRANSPORT,
//        TileSourceFactory.CLOUDMADESTANDARDTILES,
//        TileSourceFactory.CLOUDMADESMALLTILES,
//        TileSourceFactory.FIETS_OVERLAY_NL,
//        TileSourceFactory.BASE_OVERLAY_NL,
//        TileSourceFactory.ROADS_OVERLAY_NL,
//        TileSourceFactory.HIKEBIKEMAP,
//        TileSourceFactory.OPEN_SEAMAP,
//        TileSourceFactory.USGS_TOPO,
//        TileSourceFactory.USGS_SAT,
//        TileSourceFactory.ChartbundleWAC,
//        TileSourceFactory.ChartbundleENRH,
//        TileSourceFactory.ChartbundleENRL,
//        TileSourceFactory.OpenTopo,
//        TileSourceFactory.ChartbundleWAC
//    )

    private var clickCounter = 0
    private fun initView() {
        binding?.apply {
            text.setOnClickListener {
//                Utils.showToast(
//                    requireContext(),
//                    "$clickCounter | ${listOfTile[clickCounter].name()}"
//                )
//                binding?.mapView?.setTileSource(listOfTile[clickCounter])
//                clickCounter++
//                if (clickCounter >= listOfTile.size) {
//                    clickCounter = 0
//                }
            }
        }
    }

    val mumbai = GeoPoint(19.0760, 72.8777)
    val pune = GeoPoint(18.5204, 73.8567)
    private fun initMap() {
        Configuration.getInstance().load(
            requireContext(),
            SharedPref.getSharedPrefObject(requireContext())
        )
        binding?.mapView?.apply {
            this.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT)
//            this.setZoomRounding(true)
//            this.setMultiTouchControls(true)
            val mapController = controller
            mapController.setZoom(28.0)
            mapController.setCenter(mumbai)
            mapController.zoomToSpan(mumbai.latitude, mumbai.latitude)

            val path = Path()
            path.addRect(0F, 2F, 1F, 2F, Path.Direction.CW)
            PathBuilder(path)


            val myPath = Polyline(this)
            myPath.width = 3F
            myPath.setPoints(listOf(mumbai, pune))
            myPath.isGeodesic = true
            myPath.setDensityMultiplier(1.3F)
            myPath.setDowngradeDisplay(true)
//            myPath.addPoint(mumbai)
//            myPath.addPoint(pune)
            overlays.add(myPath)

            val items = ArrayList<OverlayItem>()
            val overlayItem = OverlayItem("Marker", "DIS", mumbai)
            val overlayItem1 = OverlayItem("Marker", "DIS", pune)
            items.add(overlayItem)
            items.add(overlayItem1)

            val overlay = ItemizedOverlayWithFocus(
                items,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_location_on_24),
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_location_on_24),
                R.color.purple_500,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                        return true
                    }

                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                        return false
                    }
                },
                requireContext()
            )
            overlay.setFocusItemsOnTap(true)
            this.overlays.add(overlay)
        }
    }

    override fun onResume() {
        super.onResume()
        initMap()
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().save(
            requireContext(),
            SharedPref.getSharedPrefObject(requireContext())
        )
        binding?.mapView?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toArray(arrayOfNulls(0)),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

}