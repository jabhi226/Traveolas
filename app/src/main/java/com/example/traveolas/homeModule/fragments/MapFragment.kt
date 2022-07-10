package com.example.traveolas.homeModule.fragments

import android.content.Context
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.traveolas.R
import com.example.traveolas.databinding.FragmentMapBinding
import com.example.traveolas.utils.SharedPref
import com.example.traveolas.utils.Utils
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.PathBuilder
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initMap()
        initListener()
    }

    private fun initListener() {
        binding?.apply {
            recenter.setOnClickListener(this@MapFragment)
        }
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

    var mumbai = GeoPoint(19.0760, 72.8777)
    val pune = GeoPoint(18.5204, 73.8567)
    private fun initMap() {
        Configuration.getInstance().load(
            requireContext(),
            SharedPref.getSharedPrefObject(requireContext())
        )
        binding?.apply {
            mapView.apply {
                this.setZoomRounding(false)
                this.setTileSource(TileSourceFactory.USGS_SAT)
                this.setMultiTouchControls(true)

                val mLocationOverlay =
                    MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), this)
                val bitmap = Utils.drawableToBitmap(
                    requireContext(),
                    R.drawable.ic_location__person__filled_1
                )
                mLocationOverlay.setPersonIcon(bitmap)
                mLocationOverlay.enableAutoStop = true
                mLocationOverlay.enableFollowLocation()
                mLocationOverlay.enableMyLocation()
//                mumbai = mLocationOverlay.myLocation
                overlays.add(mLocationOverlay)

                this.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
                this.zoomController.display.setBitmaps(bitmap, bitmap, bitmap, bitmap)

                val mapController = controller
                mapController.setCenter(mumbai)
                mapController.setZoom(this.maxZoomLevel - 10)

                val path = Path()
                path.addRect(0F, 2F, 1F, 2F, Path.Direction.CW)
                PathBuilder(path)

                val myPath = Polyline(binding?.mapView)
                myPath.width = 3F
                myPath.color = R.color.primary_blue
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

                val pointers = ItemizedOverlayWithFocus(
                    items,
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_location__person__filled_1
                    ),
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_location__person__filled_1
                    ),
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
                pointers.setFocusItemsOnTap(true)
                this.overlays.add(pointers)
            }
        }
        binding?.mapView?.apply {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initMap()
    }

    override fun onResume() {
        super.onResume()
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

    override fun onClick(p0: View?) {
        when (p0?.id){
            binding?.recenter?.id -> {

            }
        }
    }

}