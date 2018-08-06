package com.example.damia.aktywnimobileapp.VIEW


import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.damia.aktywnimobileapp.R
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import com.example.damia.aktywnimobileapp.API.addFragment
import com.example.damia.aktywnimobileapp.API.replaceFragment
import android.view.MotionEvent
import android.view.GestureDetector






class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,  GestureDetector.OnDoubleTapListener, TopPanel.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener,EventAddkFragment.OnFragmentInteractionListener, EventSearchFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener, FreindsFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener {



    var gDetector: GestureDetectorCompat? = null
    var choice=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.gDetector = GestureDetectorCompat(this, this)
        gDetector?.setOnDoubleTapListener(this)


        addFragment(MainFragment.newInstance(), R.id.body)

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
        }
        tvHomeLogo.setOnClickListener {
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,1)
            choice=1
        }



    }





    override fun onFling(motionEvent1: MotionEvent, motionEvent2: MotionEvent, X: Float, Y: Float): Boolean {

        val tvHistoryLogo =  this.findViewById(R.id.TVHistoryLogo)  as TextView

        val tvFriendsLogo =  this.findViewById(R.id.TVFriendsLogo)  as TextView

        val tvHomeLogo =  this.findViewById(R.id.TVHomeLogo)  as TextView


        val tvEventLogo =  this.findViewById(R.id.TVEventLogo)  as TextView



        if (motionEvent1.x - motionEvent2.x > 50) {
            Log.i("HHHHa",choice.toString())
            if(choice<4) ++choice else choice=1
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,choice)



            return true
        }

        if (motionEvent2.x - motionEvent1.x > 50) {
            Log.i("HHHHb",choice.toString())
            if(choice>1) --choice else choice=4
            changeMenuChoise(tvHomeLogo, tvEventLogo, tvFriendsLogo, tvHistoryLogo,choice)
            return true
        } else {
            return true
        }
    }

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
                replaceFragment(MainFragment.newInstance(), R.id.body)
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

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gDetector?.onTouchEvent(event)
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event)
    }
}
