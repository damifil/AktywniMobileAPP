package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.Adapters.AddFriendListAdapter
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter
import com.example.damia.aktywnimobileapp.PRESENTER.AddFriendPresenter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_add_friend.*
import kotlinx.android.synthetic.main.fragment_event.*
import mehdi.sakout.fancybuttons.FancyButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddFriendFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddFriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddFriendFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var presenter:AddFriendPresenter?=null


    var delay: Long = 1000 // 1 seconds after user stops typing
    var last_text_edit: Long = 0
    var handler = Handler()

    private val input_finish_checker = Runnable {
        if (System.currentTimeMillis() > last_text_edit + delay - 500) {
           presenter!!.search(etLoginSearch.text.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_add_friend, container, false)

        var rv=rootView.findViewById(R.id.rv_friend_add_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter=AddFriendPresenter(this)
        presenter!!.model.eventId=param1!!
        presenter!!.init()


        etLoginSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                handler.removeCallbacks(input_finish_checker)
           // presenter!!.search(s.toString())
            }
        })



    }

    fun setAdapter() {
        rv_friend_add_list.adapter = AddFriendListAdapter(presenter!!.model.userList, context!!,param1!!)
        rv_friend_add_list.adapter.notifyDataSetChanged()
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
         * @return A new instance of fragment AddFriendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddFriendFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
