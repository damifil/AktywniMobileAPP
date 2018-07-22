package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.damia.aktywnimobileapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MenuFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }


    override  fun onViewCreated(view:View ,savedInstanceState: Bundle? )
    {

        val tf = Typeface.createFromAsset(context?.assets,
                "fonts/fa-solid-900.ttf")
        val tvHistoryLogo =  view.findViewById(R.id.TVHistoryLogo)  as TextView;
        tvHistoryLogo.typeface=tf;

        tvHistoryLogo.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
            activity!!.finish()
        }

        val tvEventLogo =  view.findViewById(R.id.TVEventLogo)  as TextView
        tvEventLogo.typeface=tf

        tvEventLogo.setOnClickListener {
            val intent = Intent(context, EventActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)

            activity!!.finish()

        }


        val tvHomeLogo =  view.findViewById(R.id.TVHomeLogo)  as TextView
        tvHomeLogo.typeface=tf
        tvHomeLogo.setOnClickListener {
            val intent = Intent(    activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
            activity!!.finish()
        }

        val tvFriendsLogo =  view.findViewById(R.id.TVFriendsLogo)  as TextView
        tvFriendsLogo.typeface=tf
        tvFriendsLogo.setOnClickListener {

            val intent = Intent(activity, FriendsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
            activity!!.finish()
        }

        when (param1) {
            "1" -> tvHomeLogo.setTextColor(ContextCompat.getColor(context!!, R.color.button_color))
            "2" ->  tvEventLogo.setTextColor(ContextCompat.getColor(context!!, R.color.button_color))
            "3" ->  tvFriendsLogo.setTextColor(ContextCompat.getColor(context!!, R.color.button_color))
            "4" ->  tvHistoryLogo.setTextColor(ContextCompat.getColor(context!!, R.color.button_color))
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
        * @return A new instance of fragment MenuFragment.
        */
       // TODO: Rename and change types and number of parameters
       @JvmStatic
       fun newInstance(param1: String) =
               MenuFragment().apply {
                   arguments = Bundle().apply {
                       putString(ARG_PARAM1, param1)
                   }
               }
   }
}
