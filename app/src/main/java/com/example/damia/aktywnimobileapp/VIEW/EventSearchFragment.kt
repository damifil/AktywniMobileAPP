package com.example.damia.aktywnimobileapp.VIEW

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.damia.aktywnimobileapp.R
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter
import com.example.damia.aktywnimobileapp.Adapters.ListOfSportsAdapter
import com.example.damia.aktywnimobileapp.MODEL.SportObject
import com.example.damia.aktywnimobileapp.PRESENTER.EventSearchPresenter
import kotlinx.android.synthetic.main.fragment_event_addk.view.*
import kotlinx.android.synthetic.main.fragment_event_search.*
import android.widget.Toast
import android.databinding.adapters.TextViewBindingAdapter.setTextSize
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.databinding.adapters.SeekBarBindingAdapter.setOnSeekBarChangeListener
import android.location.Location
import android.location.LocationManager
import com.example.damia.aktywnimobileapp.API.MyLocationListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventSearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: Int? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mcontext: Context? = null
    var partItem: SportObject ?=null// SportObject("Baseball", "\uf433", 2)
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mcontext!!)
    }

    fun partItemClicked(partItem: SportObject) {
        if(partItem.equals(this.partItem))
            this.partItem=null
        else
            this.partItem = partItem
        adapter!!.notifyDataSetChanged()
    }

    var presenter:EventSearchPresenter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment



        var view = inflater.inflate(R.layout.fragment_event_search, container, false)


        val rv = view.findViewById(R.id.rv_list_sport) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val rv2 = view.findViewById(R.id.rv_event_list_search) as RecyclerView
        rv2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        presenter= EventSearchPresenter(this)

        rv.rv_list_sport.adapter = ListOfSportsAdapter(presenter!!.model.Sports, context!!,false, 0, { partItem: SportObject -> partItemClicked(partItem) })
        adapter = rv.rv_list_sport.adapter



        return view
    }

     var latitude: Double? = null
     var longitude: Double? = null
    @SuppressLint("MissingPermission")
    private fun getLocalization() {


        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude

                    } else {
                        val locationListener = MyLocationListener()
                        val locationMangaer = mcontext!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, locationListener)
                        locationMangaer.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10f, locationListener)
                    }
                }
    }

    fun setAdapter()
    {
        rv_event_list_search.adapter = EventListAdapter(presenter!!.model.eventList, context!!)
        rv_event_list_search.adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button4.setOnClickListener {
            if(partItem!=null) {
                presenter!!.search(ET_event_name.text.toString(), partItem!!.id)
            }
            else
            {
                presenter!!.search(ET_event_name.text.toString(), -1)
            }
            getLocalization()
        }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub

                textView17.text=(progress+5).toString()+" km"


            }
        })
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            mcontext = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun getPointOfView(view: View): Point {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return Point(location[0], location[1])
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
         * @return A new instance of fragment EventSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
                EventSearchFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                        putInt(ARG_PARAM2, param2)
                    }
                }
    }
}
