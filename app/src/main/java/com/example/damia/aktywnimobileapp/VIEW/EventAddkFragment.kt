package com.example.damia.aktywnimobileapp.VIEW

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.Adapters.ListOfSportsAdapter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_event_addk.view.*
import android.widget.*
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.*
import com.example.damia.aktywnimobileapp.MODEL.SportObject
import com.example.damia.aktywnimobileapp.PRESENTER.EventAddPresenter
import kotlinx.android.synthetic.main.fragment_event_addk.*
import java.util.*
import com.example.damia.aktywnimobileapp.MODEL.EventAddModel

class EventAddkFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    // TODO: Rename and change types of parameters
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var listener: OnFragmentInteractionListener? = null
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var presenter: EventAddPresenter? = null
    private var eventId: Int = 0
    var partItem: SportObject = SportObject("Baseball", "\uf433", 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble("latitude")
            longitude = it.getDouble("longitude")
            eventId = it.getInt("eventId")
        }
    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
    }

    fun partItemClicked(partItem: SportObject) {
        this.partItem = partItem
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        presenter = EventAddPresenter(this)
        val modelString = sharedPreferenceApi.getString(context!!, EnumChoice.EventAddPresenter)

        if (modelString != "") {
            presenter!!.model = Klaxon().parse<EventAddModel>(modelString)!!
        } else {
            presenter = EventAddPresenter(this)
            presenter!!.model.eventId = eventId
        }

        if (latitude != 0.0 && longitude != 0.0) {
            presenter!!.model.latitude = latitude!!
            presenter!!.model.longitude = longitude!!
        }
        if(eventId>0)
        {
            presenter!!.model.eventId=eventId
        }

        val view = inflater.inflate(R.layout.fragment_event_addk, container, false)
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)


                val cy = view.getHeight()
                val cx = view.getWidth()

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
        val rv = view.findViewById(R.id.rv_list_sport) as RecyclerView
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

            val dpd = DatePickerDialog(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val monthYear = monthOfYear + 1
                var day = ""
                var month = ""
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth
                } else {
                    day = dayOfMonth.toString()
                }

                if (monthYear < 10) {
                    month = "0" + monthYear
                } else {
                    month = monthYear.toString()
                }



                presenter!!.model.date = year.toString() + "." + month + "." + day
                tv_date.setText("Data: " + presenter!!.model.date)

                timePicker()
            }, year, month, day)
            dpd.show()
        }

        tvIco = view.findViewById(R.id.tv_ico_maps) as TextView
        tvIco.setTypeface(tf)
        tvIco.setText("\uf279")


        tvIco.setOnClickListener()
        {

            presenter!!.model.description = editText.text.toString()
            presenter!!.model.eventName = ETName.text.toString()
            sharedPreferenceApi.set(context!!, Klaxon().toJsonString(presenter!!.model), EnumChoice.EventAddPresenter)

            val newFragment = MainFragment.newInstance(false)
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.body, newFragment)
            transaction.commit()
        }

        val btn = view.findViewById(R.id.btCreate) as Button

        btn.setOnClickListener() {

            presenter!!.model.description = editText.text.toString()
            presenter!!.model.eventName = ETName.text.toString()

            if (presenter!!.model.isCorrectData()) {

                presenter!!.request()
            } else {
                Toast.makeText(context, "wypełnij wszystkie dane", Toast.LENGTH_LONG).show()
            }
        }


        rv.rv_list_sport.adapter = ListOfSportsAdapter(presenter!!.model.Sports, context!!, { partItem: SportObject -> partItemClicked(partItem) })

        adapter = rv.rv_list_sport.adapter


        return view
    }


    fun updateData() {
        ETName.setText(presenter!!.model.eventName,TextView.BufferType.EDITABLE)
        tv_date.text = "Data: " + presenter!!.model.date
        editText.setText(presenter!!.model.description, TextView.BufferType.EDITABLE)
        tv_ico_calendar.setTextColor(ContextCompat.getColor(context!!, R.color.button_color_not_choice_alternative_green))
        tv_ico_maps.setTextColor(ContextCompat.getColor(context!!, R.color.button_color_not_choice_alternative_green))
    }

    private fun timePicker() {

        val c: Calendar = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, TimePickerDialog.OnTimeSetListener { view, mHour, mMinute ->
            tv_date.append("  " + mHour.toString() + ":" + mMinute.toString())

            var minute = ""
            var hour = ""
            if (mMinute < 10) {
                minute = "0" + mMinute
            } else {
                minute = mMinute.toString()
            }
            if (mHour < 10) {
                hour = "0" + mHour.toString()
            } else {
                hour = mHour.toString()
            }

            presenter!!.model.date += " " + hour + ":" + minute
            tv_ico_calendar.setTextColor(ContextCompat.getColor(context!!, R.color.button_color_not_choice_alternative_green))


        }, mHour, mMinute, true)

        timePickerDialog.show()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (presenter!!.model.eventId > 0) {
            presenter!!.initEventChange()
        } else {
            if (presenter!!.model.latitude != 0.0) {
                tv_ico_maps.setTextColor(ContextCompat.getColor(context!!, R.color.button_color_not_choice_alternative_green))
                textView5.setText("Miejsce wybrano")
            }

            if (presenter!!.model.date != "Data:") {
                if (presenter!!.model.date == "") {
                    presenter!!.model.date = "Data:"
                } else {
                    tv_ico_calendar.setTextColor(ContextCompat.getColor(context!!, R.color.button_color_not_choice_alternative_green))
                }
            }

            view.ETName.setText(presenter!!.model.eventName)
            view.editText.setText(presenter!!.model.description)
            view.tv_date.text = (presenter!!.model.date)
        }
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

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(latitude: Double, longitude: Double, eventId: Int) =
                EventAddkFragment().apply {
                    arguments = Bundle().apply {
                        putDouble("latitude", latitude)
                        putDouble("longitude", longitude)
                        putInt("eventId", eventId)
                    }
                }
    }
}
