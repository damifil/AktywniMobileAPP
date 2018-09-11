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

import com.example.damia.aktywnimobileapp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import android.widget.TextView
import com.example.damia.aktywnimobileapp.MapClass.OnInfoWindowElemTouchListener
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.MyLocationListener
import com.example.damia.aktywnimobileapp.MapClass.MapWrapperLayout
import com.example.damia.aktywnimobileapp.PRESENTER.MainFragmentPresenter
import com.google.android.gms.maps.model.BitmapDescriptorFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var fromMain: Boolean? = null
    private var listener: OnFragmentInteractionListener? = null


    private var mGoogleMap: GoogleMap? = null
    private var mMapView: MapView? = null
    private var mView: View? = null
    private var mcontext: Context? = null

    private var infoWindow: ViewGroup? = null
    private var infoTitle: TextView? = null
    private var infoSnippet: TextView? = null
    private var score: TextView? = null
    private var event:String=""
    private var infoButton: Button? = null
    private var infoButtonListener: OnInfoWindowElemTouchListener? = null
    private var mapWrapperLayout: MapWrapperLayout? = null
    private var markerHandle: Marker? = null
    private var presenter: MainFragmentPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromMain = it.getBoolean("fromMain")
            event=it.getString("event")
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mcontext!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_main, container, false)

        this.infoWindow = inflater.inflate(R.layout.info_window, null) as ViewGroup
        presenter = MainFragmentPresenter(this,fromMain!!,event)
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

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(mcontext)
        var a= getArguments()!!.getBoolean("fromMain")
        mGoogleMap = googleMap
        mapWrapperLayout!!.init(googleMap, getPixelsFromDp(mcontext!!, 59f))
        this.infoButtonListener = object : OnInfoWindowElemTouchListener(infoButton,
                mcontext!!.resources.getDrawable(R.drawable.round_but_green_sel, null), //btn_default_normal_holo_light
                mcontext!!.resources.getDrawable(R.drawable.round_but_red_sel, null)) //btn_default_pressed_holo_light
        {

            override fun onClickConfirmed(v: View, marker: Marker) {
                val position = marker.position


                if(fromMain!!) {
                    val newFragment = CurentEventFragment.newInstance(marker.title,"")
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.body, newFragment)
                      transaction.addToBackStack(null)
                    transaction.commit()
                }
                else {
                    Toast.makeText(mcontext, "kontynujemy tworzenie", Toast.LENGTH_SHORT).show()
                    val newFragment = EventAddkFragment.newInstance(position.latitude,position.longitude,-1) //nieistotny 3 parametr i tak odczyta z sharedpreference model
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.body, newFragment)
                    //  transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
        this.infoButton!!.setOnTouchListener(infoButtonListener)


        googleMap.setInfoWindowAdapter(object : InfoWindowAdapter {
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
        presenter!!.setEvent(event)
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


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(fm:Boolean) =
                MainFragment().apply {
                    arguments = Bundle().apply {putBoolean("fromMain",fm)
                        putString("event","")
                    }
                }
        @JvmStatic
        fun newInstance(fm:String) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putString("event",fm)
                        putBoolean("fromMain",false)}
                }
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

    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

