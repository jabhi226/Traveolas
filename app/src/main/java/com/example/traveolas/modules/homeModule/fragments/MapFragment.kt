package com.example.traveolas.modules.homeModule.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.traveolas.R
import com.example.traveolas.databinding.FragmentMapBinding
import com.example.traveolas.modules.myTrackModule.activities.MyTrackActivity
import com.example.traveolas.utils.LocationHelper
import com.example.traveolas.utils.SharedPref
import com.example.traveolas.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class MapFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding
    private var locationHelper: LocationHelper? = null

    private var flow: Flow<Location>? = null

    private var mapController: IMapController? = null
    private var zoomController: CustomZoomButtonsController? = null
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private var mCompassOverlay: CompassOverlay? = null
    private var mRotationGestureOverlay: RotationGestureOverlay? = null

    private var currentGeoPoint: GeoPoint? = null

    private var mumbai = GeoPoint(19.0760, 72.8777)
    private val pune = GeoPoint(18.5204, 73.8567)

    private fun showPopup(v : View){
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.my_track_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_action_my_track-> {
                    val i = Intent(requireContext(), MyTrackActivity::class.java)
                    startActivity(i)
                }
            }
            true
        }
        popup.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLocation()
        initMap()
        initListener()
    }

    private fun initLocation() {
        locationHelper = LocationHelper(requireContext())
//        val location = locationHelper?.location
//        if (location != null) {
//            binding?.coordinates?.text =
//                Utils.convertLatLongToDMS(location.latitude, location.longitude)
//        }
//        CoroutineScope(Dispatchers.Default).launch {
//            flow?.collectLatest { location ->
//                Log.d("Updated_Location -->", "${location.latitude} | ${location.longitude}")
//                binding?.coordinates?.text =
//                    Utils.convertLatLongToDMS(location.latitude, location.longitude)
//            }
//        }
    }

    private fun initListener() {
        binding?.apply {
            recenter.setOnClickListener(this@MapFragment)
            menu.setOnClickListener(this@MapFragment)
        }
    }

    private fun initMap() {
        currentGeoPoint = mumbai

        Configuration.getInstance().load(
            requireContext(),
            SharedPref.getSharedPrefObject(requireContext())
        )
        binding?.apply {
            mapView.apply {
                setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                val locationIcon = Utils.drawableToBitmap(
                    requireContext(),
                    R.drawable.ic_location__person__filled_1
                )

//                maxZoomLevel = 9.0
                minZoomLevel = 19.0

                setLocationOverlay(locationIcon)
                overlays.add(mLocationOverlay)

                //compass overlay
//                setCompassOverlay()
//                overlays.add(mCompassOverlay)

                //rotation gesture overlay
                setRotationGestureOverlay()
                overlays.add(mRotationGestureOverlay)

                this@MapFragment.zoomController = this.zoomController
                setZoomController()

                this@MapFragment.mapController = controller
                setMapController()

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
    }

    private fun setMapController() {
        val latLong = locationHelper?.location
        if (latLong != null) {
            mapController?.setCenter(GeoPoint(latLong.latitude, latLong.longitude))
            Utils.showToast(requireContext(), "setMapController")
        }
        mapController?.setZoom(19.0)
    }

    private fun setZoomController() {
        zoomController?.activate()
        zoomController?.setZoomInEnabled(true)
        zoomController?.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
    }

    private fun setRotationGestureOverlay() {
        mRotationGestureOverlay = RotationGestureOverlay(binding?.mapView)
        mRotationGestureOverlay?.isEnabled = true
        mRotationGestureOverlay?.isOptionsMenuEnabled = true
        binding?.mapView?.setMultiTouchControls(true)
    }

    private fun setCompassOverlay() {
        mCompassOverlay =
            CompassOverlay(context, InternalCompassOrientationProvider(context), binding?.mapView)
        mCompassOverlay?.enableCompass()
        mCompassOverlay?.isPointerMode = true
        mCompassOverlay?.isOptionsMenuEnabled = true
    }

    private fun setLocationOverlay(locationIcon: Bitmap) {
        mLocationOverlay =
            MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), binding?.mapView)
        mLocationOverlay?.setPersonIcon(locationIcon)
        mLocationOverlay?.enableAutoStop = true
        mLocationOverlay?.enableMyLocation()
        mLocationOverlay?.enableFollowLocation()

//        mLocationOverlay?.setDirectionIcon(
//            Utils.drawableToBitmap(
//                requireContext(),
//                R.drawable.ic_recenter
//            )
//        )
//        mLocationOverlay?.isOptionsMenuEnabled = true
//        val abc = mLocationOverlay?.mMyLocationProvider
//        abc?.startLocationProvider { location, source ->
//            Utils.showToast(requireContext(), "startLocationProvider")
//            setNewLocation(GeoPoint(location.latitude, location.longitude))
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCreateView(LayoutInflater.from(context), null, null)
//        binding = FragmentMapBinding.inflate(LayoutInflater.from(context), null, false)
        Configuration.getInstance().load(getContext(), SharedPref.getSharedPrefObject(requireContext()))
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
        _binding = null
    }

    private var i = 1
    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.recenter?.id -> {
                locationHelper?.location?.let {
                    i++
                    setNewLocation(GeoPoint(it.latitude + (0.0002 * i), it.longitude + (0.0002 * i)))
                }
            }
            binding?.menu?.id -> {
                binding?.menu?.let { showPopup(it) }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNewLocation(location: GeoPoint) {
        Utils.showToast(requireContext(), "setLocationText")
        binding?.coordinates?.text =
            Utils.convertLatLongToDMS(
                location.latitude,
                location.longitude
            ) + " : ${location.altitude}"
        mapController?.animateTo(
            location,
            19.0,
            800L
        )
        addPoyLine(location)
    }

    private fun addPoyLine(newGeoPoint: GeoPoint) {
        if (currentGeoPoint == null) {
            currentGeoPoint = newGeoPoint
        }
        binding?.mapView?.let {
            val myPath = Polyline()
            myPath.width = 3F
            myPath.color = R.color.primary_blue
            myPath.setPoints(listOf(currentGeoPoint, newGeoPoint))
            myPath.isGeodesic = true
            myPath.setDensityMultiplier(2.3F)
            myPath.setDowngradeDisplay(true)
            binding?.mapView?.overlays?.add(myPath)
            currentGeoPoint = newGeoPoint
            addCheckPoint(currentGeoPoint!!)
        }
    }

    private fun addCheckPoint(location: GeoPoint)
    {
        binding?.mapView?.let {
            val marker = Marker(it)
            marker.icon = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_user__avatar_1
            )
            marker.position = location
//        marker.setOnMarkerClickListener { marker, mapView ->
//            Utils.showToast(mapView.context, marker.id.toString())
//            false
//        }
            binding?.mapView?.overlays?.add(marker)
        }
    }

}