package com.example.damia.aktywnimobileapp.VIEW

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.RecyclerItemClickListener
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter
import com.example.damia.aktywnimobileapp.Adapters.ListOfSportsAdapter
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.MODEL.Sport

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.fragment_event_addk.view.*
import mehdi.sakout.fancybuttons.FancyButton
import android.widget.TimePicker
import android.text.format.DateFormat.is24HourFormat
import java.text.DateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventAddkFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventAddkFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventAddkFragment : Fragment(), TimePickerDialog.OnTimeSetListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
    }
    private fun partItemClicked(partItem: Sport) {
       // Toast.makeText(context, "Clicked: ${partItem.name}", Toast.LENGTH_LONG).show()
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_event_addk, container, false)
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)


                val cy = view.getHeight()
                val cx = view.getWidth()
                // get the final radius for the clipping circle
                val finalRadius = Math.max(view.getWidth(), view.getHeight())

                // create the animator for this view (the start radius is zero)
                val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                        100f, finalRadius.toFloat())
                anim.duration = 500

                // make the view visible and start the animation
                view.setVisibility(View.VISIBLE)
                anim.start()
            }
        })
        var rv = view.findViewById(R.id.rv_list_sport) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val tf = Typeface.createFromAsset(context!!.assets,
                "fonts/fa-solid-900.ttf")

        var tvIco = view.findViewById(R.id.tv_ico_calendar) as TextView
        tvIco.setTypeface(tf)
        tvIco.setText("\uf073")

        tvIco.setOnClickListener()
        {


            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                //Toast.makeText (context,"" + dayOfMonth + " " + monthOfYear + ", " + year,Toast.LENGTH_LONG).show()
            }, year, month, day)
            dpd.show()
        }

        tvIco = view.findViewById(R.id.tv_ico_maps) as TextView
        tvIco.setTypeface(tf)
        tvIco.setText("\uf279")




//////
        var arr: ArrayList<Sport> = ArrayList()
        var a1 = Sport()
        a1.name = "kolarstwo"
        a1.code = "\uF206"
        var a2 = Sport()
        a2.name = "futbol"
        a2.code = "\uf1e3"
        arr.add(a1)
        arr.add(a2)
        arr.add(a2)
        arr.add(a2)
        arr.add(a2)
        arr.add(a2)
        arr.add(a2)
        arr.add(a2)
/////////
        rv.rv_list_sport.adapter = ListOfSportsAdapter(arr, context!!, { partItem: Sport -> partItemClicked(partItem) })

       adapter=rv.rv_list_sport.adapter

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
         * @return A new instance of fragment EventAddkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EventAddkFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
