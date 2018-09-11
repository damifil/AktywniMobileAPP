package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter
import com.example.damia.aktywnimobileapp.Adapters.FriendListAdapter
import com.example.damia.aktywnimobileapp.PRESENTER.FriendsPresenter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_freinds.*
import mehdi.sakout.fancybuttons.FancyButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FreindsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FreindsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FreindsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var presenter:FriendsPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_freinds, container, false)

        var rv=rootView.findViewById(R.id.rv_friend_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)


        var rv2=rootView.findViewById(R.id.rv_friend_inActive_list) as RecyclerView
        rv2.layoutManager = LinearLayoutManager(context)

        val buttonAdd = rootView.findViewById(R.id.btn_add) as FancyButton
        buttonAdd.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                val newFragment = AddFriendFragment.newInstance("","")
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
        TVFriendsInactiveListText.visibility=View.INVISIBLE
        rv_friend_inActive_list.visibility=View.INVISIBLE
        presenter= FriendsPresenter(this)
        presenter!!.init()

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

    fun setAdapter()
    {
        rv_friend_list.adapter = FriendListAdapter(presenter!!.model.friendsList,context!!,presenter!!)
        rv_friend_list.adapter.notifyDataSetChanged()
    }

    fun setAdapterInvitations()
    {
        if(presenter!!.model.friendsInactive.any()) {
            TVFriendsInactiveListText.visibility = View.VISIBLE
            rv_friend_inActive_list.visibility = View.VISIBLE

            val params =  rv_friend_inActive_list.getLayoutParams()
            params.height = 300
            rv_friend_inActive_list.setLayoutParams(params)
            TVFriendsInactiveListText.setLayoutParams(ConstraintLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT))
            rv_friend_inActive_list.adapter = FriendListAdapter(presenter!!.model.friendsInactive, context!!,presenter!!)
            rv_friend_inActive_list.adapter.notifyDataSetChanged()
        }
        else
        {
            TVFriendsInactiveListText.visibility=View.INVISIBLE
            rv_friend_inActive_list.visibility=View.INVISIBLE
            val params =  rv_friend_inActive_list.getLayoutParams()
            params.height = 0
            rv_friend_inActive_list.setLayoutParams(params)
            TVFriendsInactiveListText.height=0
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
         * @return A new instance of fragment FreindsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                FreindsFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
