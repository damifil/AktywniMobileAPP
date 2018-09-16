package com.example.damia.aktywnimobileapp.VIEW

import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.damia.aktywnimobileapp.R
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.support.v4.view.GestureDetectorCompat
import com.example.damia.aktywnimobileapp.API.addFragment
import com.example.damia.aktywnimobileapp.API.replaceFragment


class MainActivity : AppCompatActivity(),CurentEventFragment.OnFragmentInteractionListener, EventChatFragment.OnFragmentInteractionListener
        , TopPanel.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener,EventAddkFragment.OnFragmentInteractionListener
        , EventSearchFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener, FreindsFragment.OnFragmentInteractionListener
        , HistoryFragment.OnFragmentInteractionListener, AddFriendFragment.OnFragmentInteractionListener,UserProfileFragment.OnFragmentInteractionListener
        , EventUsersFragment.OnFragmentInteractionListener, EditAccountFragment.OnFragmentInteractionListener   {



    var gDetector: GestureDetectorCompat? = null
    var choice=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(MainFragment.newInstance(true), R.id.body)

        val tf = Typeface.createFromAsset(this.assets,
                "fonts/fa-solid-900.ttf")
        val tvHistoryLogo =  this.findViewById(R.id.TVHistoryLogo)  as TextView
        tvHistoryLogo.typeface=tf;

        val tvFriendsLogo =  this.findViewById(R.id.TVFriendsLogo)  as TextView
        tvFriendsLogo.typeface=tf

        val tvHomeLogo =  this.findViewById(R.id.TVHomeLogo)  as TextView
        tvHomeLogo.typeface=tf


        val tvEventLogo =  this.findViewById(R.id.TVEventLogo)  as TextView
        tvEventLogo.typeface=tf

        changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,1)

        tvHistoryLogo.setOnClickListener {
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,4)
            choice=4
        }

        tvEventLogo.setOnClickListener {
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,2)
            choice=2

        }

        tvFriendsLogo.setOnClickListener {
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,3)
            choice=3
            val newFragment = CurentEventFragment.newInstance(" ","")

        }
        tvHomeLogo.setOnClickListener {
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,1)
            choice=1
        }



    }

   //override fun onBackPressed() {

    //}




    override fun onFragmentInteraction(uri: Uri) {
        //you can leave it empty
    }

    fun changeMenuChoise(tvHomeLogo: TextView, tvEventLogo:TextView, tvFriendsLogo:TextView, tvHistoryLogo:TextView, choice:Int)
    {
        when (choice) {
            1 -> {tvHomeLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color))
                tvEventLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvFriendsLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvHistoryLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                replaceFragment(MainFragment.newInstance(true), R.id.body)
            }

            2 ->  {tvEventLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color))
                tvHomeLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvFriendsLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvHistoryLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                replaceFragment(EventFragment.newInstance(this), R.id.body)
            }

            3 ->  {tvFriendsLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color))
                tvEventLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvHomeLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvHistoryLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                replaceFragment(FreindsFragment.newInstance(), R.id.body)
            }

            4 ->  {tvHistoryLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color))
                tvEventLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvHomeLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                tvFriendsLogo.setTextColor(ContextCompat.getColor(this, R.color.button_color_not_choice))
                replaceFragment(HistoryFragment.newInstance(), R.id.body)

            }

        }
    }




}
