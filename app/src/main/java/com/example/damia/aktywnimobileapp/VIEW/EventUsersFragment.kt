package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.Adapters.FriendListAdapter
import com.example.damia.aktywnimobileapp.Adapters.UserEventAdapter
import com.example.damia.aktywnimobileapp.PRESENTER.EventUsersPresenter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_event_users.*
import kotlinx.android.synthetic.main.fragment_freinds.*
import mehdi.sakout.fancybuttons.FancyButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventUsersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventUsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventUsersFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var adminLogin: String? = null
    private var eventId: Int? = null
    private var listener: OnFragmentInteractionListener? = null
    var presenter:EventUsersPresenter?=null
    var eventName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            adminLogin = it.getString(ARG_PARAM1)
            eventId = it.getInt(ARG_PARAM2)
            eventName= it.getString("eventName")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_event_users, container, false)

        var rv=rootView.findViewById(R.id.rv_users_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)

        val buttonAdd = rootView.findViewById(R.id.btn_add) as FancyButton
        buttonAdd.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                sharedPreferenceApi.set(context!!, Klaxon().toJsonString(presenter!!.model.friendsList), EnumChoice.usersEvent)
                val newFragment = AddFriendFragment.newInstance(presenter!!.model.eventID.toString(),"")
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.body, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })


        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter=EventUsersPresenter(this,eventId!!,adminLogin!!)
        TVTitleUsersEvent.text="Uczestnicy wydarzenia: "+eventName
    }

    fun setAdapter()
    {
        rv_users_list.adapter = UserEventAdapter(presenter!!.model.friendsList,context!!,presenter!!)   //FriendListAdapter(presenter!!.model.friendsList,context!!,presenter!!)
        rv_users_list.adapter.notifyDataSetChanged()
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
         * @return A new instance of fragment EventUsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: Int,param3:String) =
                EventUsersFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putInt(ARG_PARAM2, param2)
                        putString("eventName",param3)
                    }
                }
    }
}
