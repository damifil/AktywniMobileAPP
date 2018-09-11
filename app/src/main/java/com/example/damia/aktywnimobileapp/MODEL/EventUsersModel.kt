package com.example.damia.aktywnimobileapp.MODEL

import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi

class EventUsersModel
{
    var userLogin=""

    var eventID=0
    var friendsList:MutableList<User> = ArrayList()
    var adminLogin=""
    var userIsAdmin=false

}