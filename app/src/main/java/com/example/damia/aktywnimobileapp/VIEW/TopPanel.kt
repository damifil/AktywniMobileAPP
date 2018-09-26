package com.example.damia.aktywnimobileapp.VIEW

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.widget.TextView
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.R
import android.widget.PopupMenu
import android.widget.Toast
import android.view.*
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import kotlinx.android.synthetic.main.fragment_top_panel.*
import org.json.JSONObject
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.view.SubMenu
import android.util.TypedValue
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.MODEL.sports
import com.example.damia.aktywnimobileapp.R.color.text_color_alternative
import com.example.damia.aktywnimobileapp.R.color.yellow_color
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class messageHeader {
    var UserFromId = ""
    var UserLoginFrom = ""
}

class TopPanel : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    private var messageList = ArrayList<messageHeader>()
    var handler: Handler = Handler()
    var eventList: ArrayList<EventListItem> = ArrayList()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var tf = Typeface.createFromAsset(context?.assets,
                "fonts/Pacifico-Regular.ttf")
        val tvLogo = view.findViewById(R.id.TVFLogo) as TextView
        tvLogo.typeface = tf

        if(!sharedPreferenceApi.getString(context!!,EnumChoice.isAdmin).equals("uzytkownik"))
        {
            TVFLogo.text="Aktywni.pl\npremium"
        }

        tf = Typeface.createFromAsset(context?.assets,
                "fonts/fa-solid-900.ttf")
        val tvIntent = view.findViewById(R.id.TVFIntence) as TextView
        tvIntent.setTypeface(tf)


        val tvSettings = view.findViewById(R.id.TVFSettings) as TextView
        tvSettings.setTypeface(tf)
        tvSettings.setOnClickListener {

            val popup = PopupMenu(context, tvSettings)
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu, popup.getMenu())

            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    if (item.title == "Wyloguj") {
                        val intent = Intent(context, LoginActivity::class.java)
                        sharedPreferenceApi.set(context!!, "", EnumChoice.token)
                        startActivity(intent)
                        activity!!.finish()
                    } else if (item.title == "Mój profil") {
                        val newFragment = UserProfileFragment.newInstance("-1", "")
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.body, newFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    } else {
                        Toast.makeText(context, "Kliknieto " + item.title, Toast.LENGTH_SHORT).show()

                    }
                    return true
                }
            })
            popup.show()
        }
        tf = Typeface.createFromAsset(context?.assets,
                "fonts/fa-regular-400.ttf")
        val tvMessage = view.findViewById(R.id.TVFMessage) as TextView
        tvMessage.setText("\uf075")
        tvMessage.setTypeface(tf)

        var toSend = HashMap<String, String>()

        val runnableCode = object : Runnable {
            override fun run() {
                try {
                    HTTPRequestAPI(this, "messageUser/isUnreadMessages", "isUnreadResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context!!, EnumChoice.token)), "GET").execute()
                } catch (e: Exception) {
                }

                try {
                    HTTPRequestAPI(this, "event/notComments", "notComentsEvent", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context!!, EnumChoice.token)), "GET").execute()
                } catch (e: Exception) {
                }


                handler.postDelayed(this, 30000)
            }


            fun notComentsEvent(result: String)
            {
                val ico = TVFIntence



                val root= JSONObject(result)
                if (root.getString("response").equals("True")) {
                   // ico.setTypeface(tf)
                    val jsonArray: JSONArray = root.getJSONArray("info")
                    if(jsonArray.length()>0){
                        TVFMessage.setTextColor(resources.getColor(yellow_color))
                    }
                    else
                    {
                        TVFMessage.setTextColor(resources.getColor(text_color_alternative))
                    }

                    val popup = PopupMenu(context, ico)


                    eventList.clear()
                    if(jsonArray.length()>0)
                    {
                        ico.setTextColor( resources.getColor(R.color.yellow_color))
                    }
                    for (i in 0..jsonArray.length() - 1) {
                        val item = jsonArray.getJSONObject(i)
                        val event: EventListItem = EventListItem(item.getString("name"), item.getString("description"), item.getString("date").replace('T',' '), sports.values()[item.getInt("disciplineId")-2].ico   )
                        event.eventID = item.getInt("eventId")
                        event.adminLogin=item.getString("adminLogin")

                        eventList.add(event)

                      //  ico.setOnClickListener {
                                    popup.menu.add(event.name)

                    //            }




                    }

                    popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem): Boolean {

                            val event = eventList.filter { it -> it.name.equals(item.title) }
                            val newFragment = EventRatingsFragment.newInstance(event.first().eventID.toString(),event.first().name)
                            val transaction = fragmentManager!!.beginTransaction()
                            transaction.addToBackStack(null)
                            transaction.replace(R.id.body, newFragment)
                            transaction.commit()
                            return true
                        }
                    })
                    ico.setOnClickListener {
                        popup.show()
                    }


                }
            }

            fun isUnreadResult(result: String) {
                val root = JSONObject(result)
                if (root.getBoolean("info")) {
                    TVFMessage.setTextColor(resources.getColor(R.color.yellow_color))


                    val tvMessage = view.findViewById(R.id.TVFMessage) as TextView

                    var tf = Typeface.createFromAsset(context?.assets,
                            "fonts/fa-solid-900.ttf")
                    tvMessage.setTypeface(tf)


                    val toSend = HashMap<String, String>()
                    try {
                        HTTPRequestAPI(this, "messageUser/headers", "messageHedersResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context!!, EnumChoice.token)), "GET").execute()
                    } catch (e: Exception) {
                    }
                }
            }

        }
        handler.post(runnableCode)


        toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "messageUser/headers", "messageHedersResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }

    }


    fun messageHedersResult(result: String) {

        val ico = TVFMessage
        var tf = Typeface.createFromAsset(context?.assets,
                "fonts/fa-regular-400.ttf")

        ico.setOnClickListener {

            ico.setTypeface(tf)
            TVFMessage.setTextColor(resources.getColor(text_color_alternative))
            val popup = PopupMenu(context, ico)
            //   popup.getMenuInflater()
            //         .inflate(R.menu.popup_menu, popup.getMenu())

            val root = JSONObject(result)
            if (root.getBoolean("response")) {
                val jarray = root.getJSONArray("info")

                for (i in 0..jarray.length() - 1) {
                    val header = messageHeader()
                    header.UserFromId = JSONObject(jarray.get(i).toString()).getString("UserFromId")
                    header.UserLoginFrom = JSONObject(jarray.get(i).toString()).getString("UserLoginFrom")
                    messageList.add(header)
                    popup.menu.add(header.UserLoginFrom)
                }
            }


            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {

                    val friend = messageList.filter { it -> it.UserLoginFrom.equals(item.title) || ("*" + it.UserLoginFrom).equals(item.title) }

                    val newFragment = EventChatFragment.newInstance(friend.first().UserLoginFrom, friend.first().UserFromId, "")
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.addToBackStack(null)
                    transaction.replace(R.id.body, newFragment)
                    transaction.commit()
                    return true
                }
            })

            popup.show()
        }
//*/
        //update
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
