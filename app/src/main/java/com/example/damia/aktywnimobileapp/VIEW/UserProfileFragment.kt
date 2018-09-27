package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.damia.aktywnimobileapp.PRESENTER.UserProfilPresenter

import com.example.damia.aktywnimobileapp.R
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.Adapters.ComentAdapter
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_user_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var userID: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var presenter : UserProfilPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userID = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_user_profile, container, false)
        var rv = rootView.findViewById(R.id.rv_comment) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)
        return rootView
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

    fun updateListOfComment()
    {
        rv_comment.adapter = ComentAdapter(presenter!!.model.coments ,context!!)
        rv_comment.adapter.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         presenter = UserProfilPresenter(this)
        presenter!!.model.userID = userID!!.toInt()
        presenter!!.downloadData()
        val tf = Typeface.createFromAsset(context!!.assets,
                "fonts/fa-solid-900.ttf")
        val tvIco = view!!.findViewById(R.id.icoAddDelete) as TextView
        tvIco.setTypeface(tf)
        tvIco.setOnClickListener {
            presenter!!.clickIco()
        }

        if (sharedPreferenceApi.getString(context!!, EnumChoice.isAdmin).equals("uzytkownik")) {
            button6.text = "przedłuż konto premium"
            button6.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://35.242.196.239/aktywni"))
                startActivity(browserIntent)}
        } else {
            button6.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://35.242.196.239/aktywni"))
                startActivity(browserIntent)
            }
        }

    }


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
         * @return A new instance of fragment UserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                UserProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
