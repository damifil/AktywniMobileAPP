package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.Adapters.EventListAdapter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_event.*
import android.support.v7.widget.RecyclerView
import android.view.ViewAnimationUtils
import mehdi.sakout.fancybuttons.FancyButton
import android.os.Build
import android.annotation.TargetApi
import android.support.constraint.ConstraintSet
import com.example.damia.aktywnimobileapp.PRESENTER.EventPresenter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    private var presenter: EventPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private var isBack: Int = 0

    private fun show(view: View) {
        // get the center for the clipping circle
        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2

        // get the final radius for the clipping circle
        val finalRadius = Math.max(view.width, view.height)

        // create the animator for this view (the start radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                0f, finalRadius.toFloat())
        anim.duration = 1000

        // make the view visible and start the animation
        view.visibility = View.VISIBLE
        anim.start()
    }

    // To hide a previously visible view using this effect:


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(R.layout.fragment_event, container, false)

     /*   if (isBack > 0) {
            rootView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)
                    // val button = rootView.findViewById(R.id.btn_search) as FancyButton
                    //  val point = getPointOfView(button)

                    val cy = 0
                    val cx = if (isBack == 2) 0 else rootView.getWidth()
                    // get the final radius for the clipping circle
                    val finalRadius = Math.max(rootView.getWidth(), rootView.getHeight())

                    // create the animator for this view (the start radius is zero)
                    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                            100f, finalRadius.toFloat())
                    anim.duration = 500

                    // make the view visible and start the animation
                    rootView.setVisibility(View.VISIBLE)
                    anim.start()
                }
            })
            isBack = 0
        }*/


        var rv = rootView.findViewById(R.id.rv_event_list_search) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)

        rv = rootView.findViewById(R.id.rvListInvitation) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)


        val buttonSearch = rootView.findViewById(R.id.btn_search) as FancyButton
        buttonSearch.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                isBack = 1
                val newFragment = EventSearchFragment()
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.body, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })


        val buttonAdd = rootView.findViewById(R.id.btn_add) as FancyButton
        buttonAdd.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                isBack = 2
                val newFragment = EventAddkFragment.newInstance(100000.0, 100000.0, -1)
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
        presenter = EventPresenter(this)
        presenter!!.downloadEvents()
       // val constraintSet2 = ConstraintSet()
      //  constraintSet2.clone(cl_event)
      //  constraintSet2.constrainHeight(R.id.rvListInvitation, 0)
     //   constraintSet2.applyTo(cl_event)
    }

    fun setAdapter() {
        rv_event_list_search.adapter = EventListAdapter(presenter!!.model.eventList, context!!)
        rv_event_list_search.adapter.notifyDataSetChanged()
    }

    fun setAdapter2() {
        rvListInvitation.adapter = EventListAdapter(presenter!!.model.eventInvitationList, context!!)
        rvListInvitation.adapter.notifyDataSetChanged()
        rvListInvitation.visibility=View.VISIBLE
        textView14.visibility=View.VISIBLE
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(cl_event)
        constraintSet2.constrainHeight(R.id.rvListInvitation, 250)
        constraintSet2.constrainHeight(R.id.rv_event_list_search, 0)
        constraintSet2.constrainHeight(R.id.textView14,0)

        constraintSet2.applyTo(cl_event)

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
         * @return A new instance of fragment EventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(context: Context) =
                EventFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}


