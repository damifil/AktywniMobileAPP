package com.example.damia.aktywnimobileapp.VIEW

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.MyLocationListener
import com.example.damia.aktywnimobileapp.MapClass.MapWrapperLayout
import com.example.damia.aktywnimobileapp.MapClass.OnInfoWindowElemTouchListener
import com.example.damia.aktywnimobileapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MapFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {


    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    private var mGoogleMap: GoogleMap? = null
    private var mMapView: MapView? = null
    private var mView: View? = null
    private var mcontext: Context? = null
    private var infoWindow: ViewGroup? = null
    private var infoTitle: TextView? = null
    private var infoSnippet: TextView? = null
    private var infoButton: Button? = null
    private var infoButtonListener: OnInfoWindowElemTouchListener? = null
    private var mapWrapperLayout: MapWrapperLayout? = null
    private var markerHandle: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mcontext!!)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(mcontext)
        mGoogleMap = googleMap
        mapWrapperLayout!!.init(googleMap, getPixelsFromDp(mcontext!!, 59f))
        this.infoButtonListener = object : OnInfoWindowElemTouchListener(infoButton,
                mcontext!!.resources.getDrawable(R.drawable.round_but_green_sel, null), //btn_default_normal_holo_light
                mcontext!!.resources.getDrawable(R.drawable.round_but_red_sel, null)) //btn_default_pressed_holo_light
        {
            override fun onClickConfirmed(v: View, marker: Marker) {
                Toast.makeText(mcontext, marker.title + "'s button clicked!", Toast.LENGTH_SHORT).show()
            }
        }
        this.infoButton!!.setOnTouchListener(infoButtonListener)


        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Setting up the infoWindow with current's marker info
                infoTitle!!.setText(marker.title)
                infoSnippet!!.setText(marker.snippet)
                infoButtonListener!!.setMarker(marker)
                mapWrapperLayout!!.setMarkerWithInfoWindow(marker, infoWindow)
                return infoWindow!!
            }
        })



        googleMap.setOnMapClickListener { point ->
            if (markerHandle != null) {
                markerHandle!!.remove()
            }
            val marker = MarkerOptions().position(
                    LatLng(point.latitude, point.longitude)).title("Twoje wydarzenie")
            markerHandle = googleMap.addMarker(marker)
        }
        getLocalization()
    }

    fun setMarker(latitude: Double, longitude: Double, title: String, description: String) {
        mGoogleMap!!.addMarker(MarkerOptions()
                .title(title)
                .snippet(description)
                .position(LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
    }

    fun getPixelsFromDp(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_map, container, false)
        this.infoWindow = inflater.inflate(R.layout.info_window, null) as ViewGroup
        return mView


    }


    private var latitude: Double? = null
    private var longitude: Double? = null
    @SuppressLint("MissingPermission")
    private fun getLocalization() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        val position = LatLng(latitude!!, longitude!!)
                        markerHandle = mGoogleMap!!.addMarker(MarkerOptions().position(position).title("twoja pozycja"))
                        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
                    } else {
                        val locationListener =  MyLocationListener()
                        val locationMangaer = mcontext!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f,locationListener)
                        locationMangaer.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10f,locationListener)
                    }
                }
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.infoTitle = infoWindow!!.findViewById(R.id.title) as TextView
        this.infoSnippet = infoWindow!!.findViewById(R.id.snippet) as TextView
        this.infoButton = infoWindow!!.findViewById(R.id.button) as Button
        mapWrapperLayout = mView!!.findViewById(R.id.map_relative_layout)
        mMapView = mView!!.findViewById(R.id.map)
        if (mMapView != null) {
            mMapView!!.onCreate(null)
            mMapView!!.onResume()
            mMapView!!.getMapAsync(this)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
