package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.damia.aktywnimobileapp.PRESENTER.EditAccountPresenter

import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.fragment_edit_account.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditAccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditAccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var presenter: EditAccountPresenter? = null
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


        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EditAccountPresenter(this)

        val tf = Typeface.createFromAsset(context!!.assets,
                "fonts/fa-solid-900.ttf")

        val t = ETPassword
        t.setTransformationMethod(PasswordTransformationMethod())
        val seeIco = TVLoginIco
        seeIco.setTypeface(tf)
        seeIco.text = "\uF06E"
        seeIco.setOnClickListener {
            seeIco.setText(if (seeIco.getText() === "\uf070") "\uf06e" else "\uf070")
            if (seeIco.getText() === "\uf070") {
                val t = ETPassword
                t.setTransformationMethod(null)
            } else {
                val t = ETPassword
                t.setTransformationMethod(PasswordTransformationMethod())
            }
        }

        val t2 = ETPasswordNew
        t2.setTransformationMethod(PasswordTransformationMethod())
        val seeIco2 = TVLoginIco2
        seeIco2.setTypeface(tf)
        seeIco2.text = "\uF06E"
        seeIco2.setOnClickListener {
            seeIco2.setText(if (seeIco.getText() === "\uf070") "\uf06e" else "\uf070")
            if (seeIco2.text === "\uf070") {
                val t = ETPasswordNew
                t.setTransformationMethod(null)
            } else {
                val t = ETPasswordNew
                t.setTransformationMethod(PasswordTransformationMethod())
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    fun updateData() {
        ETEmail.setText(presenter!!.model.email)
        ETDescribe.setText(presenter!!.model.description)
        BTChange.setOnClickListener {

            presenter!!.changedata()
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
         * @return A new instance of fragment EditAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EditAccountFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
