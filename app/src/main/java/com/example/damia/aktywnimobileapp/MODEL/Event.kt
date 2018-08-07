package com.example.damia.aktywnimobileapp.MODEL

import java.util.*

class Event
{
    var longitude:Double?=null
    var latitude:Double?=null
    var description:String=""
    var typeOfSport:String=""
    var eventName:String=""
    var date:Date?=null
    var listOfMembers:List<User>?=null

    // 0 - event w którym uczestnik nie jest
    // 1- evwent w któym uczestnik jest
    //2 event w którym nie jest i jest premium
    var eventValue=0
}