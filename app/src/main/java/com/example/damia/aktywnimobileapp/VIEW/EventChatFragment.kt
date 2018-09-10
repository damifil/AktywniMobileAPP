package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.Adapters.EventChatListAdapter
import com.example.damia.aktywnimobileapp.PRESENTER.EventChatPresenter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_event_chat.*
import kotlinx.android.synthetic.main.fragment_event_chat.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventChatFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var eventId: Int? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var adapter: EventChatListAdapter? = null
    var handler: Handler = Handler()
    var presenter: EventChatPresenter? = null
    var lastPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_event_chat, container, false)

        presenter = EventChatPresenter(this)
        presenter!!.model.eventId = eventId!!.toInt()
        var rv = rootView.findViewById(R.id.rv_event_list_chat) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)

        adapter = EventChatListAdapter(presenter!!.model.chatList, context!!)
        rootView.button2.setOnClickListener()
        {
            presenter!!.sendMessage(editText2.text.toString())
            editText2.setText("")
        }


        rv.rv_event_list_chat.adapter = adapter


        return rootView
    }

    fun Notify() {
        var listLinearLayoutManager:LinearLayoutManager=rv_event_list_chat.layoutManager as LinearLayoutManager
        if (presenter!!.model.lastchatListSize==-1 ||presenter!!.model.lastchatListSize-1<= listLinearLayoutManager.findLastCompletelyVisibleItemPosition() ) {
            rv_event_list_chat.scrollToPosition(presenter!!.model.chatList.size - 1)
        }
        adapter!!.notifyDataSetChanged()
        presenter!!.model.lastchatListSize= presenter!!.model.chatList.size
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter!!.downloadLatestMessage()
        val runnableCode = object : Runnable {
            override fun run() {
                presenter!!.updateChat()
                handler.postDelayed(this, 3000)
            }
        }
        handler.post(runnableCode)

        rv_event_list_chat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager.itemCount
                presenter!!.downloadBeforeMessage()
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
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        handler.removeCallbacksAndMessages(null)
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
         * @return A new instance of fragment EventChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
                EventChatFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
