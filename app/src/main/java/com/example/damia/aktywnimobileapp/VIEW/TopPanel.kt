package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi

import com.example.damia.aktywnimobileapp.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TopPanel.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TopPanel.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TopPanel : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_panel, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override  fun onViewCreated(view:View ,savedInstanceState: Bundle? )
    {
        var tf = Typeface.createFromAsset(context?.assets,
                "fonts/Pacifico-Regular.ttf")
        val tvLogo =  view.findViewById(R.id.TVFLogo)  as TextView
        tvLogo.typeface=tf

         tf = Typeface.createFromAsset(context?.assets,
                "fonts/fa-solid-900.ttf")
        val tvIntent = view.findViewById(R.id.TVFIntence) as TextView
        tvIntent.setTypeface(tf)


        val tvMessage = view.findViewById(R.id.TVFMessage) as TextView
        tvMessage.setTypeface(tf)
        val tvSettings = view.findViewById(R.id.TVFSettings) as TextView
        tvSettings.setTypeface(tf)
        tvSettings.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            sharedPreferenceApi.set(context!!, "", EnumChoice.token)
            startActivity(intent)
            activity!!.finish()

        }
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
         * @return A new instance of fragment TopPanel.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                TopPanel()
                }
    }
