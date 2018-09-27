package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.MODEL.Event
import com.example.damia.aktywnimobileapp.PRESENTER.CurentEventPresenter
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.databinding.FragmentCurentEventBinding
import kotlinx.android.synthetic.main.fragment_curent_event.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CurentEventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CurentEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CurentEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var eventName: String? = null
    private var adminLogin: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var presenter: CurentEventPresenter? = null
    var binding: FragmentCurentEventBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventName = it.getString(ARG_PARAM1)
            adminLogin = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        presenter = CurentEventPresenter(this, eventName!!,adminLogin!!)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_curent_event, container, false)
        binding!!.mod = presenter!!.model


        return binding!!.root
    }

    fun setIco(userStatus: Int) {
        val tf = Typeface.createFromAsset(context!!.assets,
                "fonts/fa-solid-900.ttf")
        TVIco.setTypeface(tf)
        TVIcoChangeEvent.setTypeface(tf)
        when (userStatus) {
            0 -> {
                TVIco.setText("\uf274")
                TVIco.setOnClickListener{
                    presenter!!.joinEvent()
                }
            }
            1 -> {
                TVIco.setText("\uf273")
                TVIco.setOnClickListener{
                    presenter!!.exceptEvent()
                }
            }
            2 -> {
                TVIco.setText("\uf272")
                TVIco.setOnClickListener{
                presenter!!.deleteEvent()
                }
                TVIcoChangeEvent.setText("\uf303")
                TVIcoChangeEvent.setOnClickListener{
                presenter!!.changeEvent()
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tf = Typeface.createFromAsset(context!!.assets,
                "fonts/fa-solid-900.ttf")

        var tvMapIco = view.findViewById(R.id.TVMapIco) as TextView
        tvMapIco.setTypeface(tf)
        tvMapIco.setText("\uf279")

        tvMapIco.setOnClickListener {
            val event= Event()
            event.longitude=presenter!!.model.longitude
            event.latitude=presenter!!.model.latitude
            event.description=presenter!!.model.describe.get()!!
            val newFragment = MainFragment.newInstance(Klaxon().toJsonString(event))
            val transaction = fragmentManager!!.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.body, newFragment)
            transaction.commit()
        }


        BTChat.setOnClickListener {

            val newFragment = EventChatFragment.newInstance(presenter!!.model.eventID.toString(), "",presenter!!.model.name.get()!!)
            val transaction = fragmentManager!!.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.body, newFragment)
            transaction.commit()

        }
        BTUsers.setOnClickListener{
            val newFragment = EventUsersFragment.newInstance(presenter!!.model.adminLogin,presenter!!.model.eventID,presenter!!.model.name.get()!!.removeRange(0,11))
            val transaction = fragmentManager!!.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.body, newFragment)

            transaction.commit()
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
         * @return A new instance of fragment CurentEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CurentEventFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
