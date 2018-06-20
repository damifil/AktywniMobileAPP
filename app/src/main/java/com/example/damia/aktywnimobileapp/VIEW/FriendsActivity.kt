package com.example.damia.aktywnimobileapp.VIEW

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.damia.aktywnimobileapp.R

class FriendsActivity : AppCompatActivity(), TopPanel.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        //you can leave it empty
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.topPanel, TopPanel.newInstance(), "topPanel")
                    .add(R.id.menu, MenuFragment.newInstance("3"), "menu")
                    .commit()
        }


        }
}
