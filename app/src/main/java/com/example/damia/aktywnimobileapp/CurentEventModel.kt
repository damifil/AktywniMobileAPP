package com.example.damia.aktywnimobileapp

import android.databinding.ObservableField
import com.example.damia.aktywnimobileapp.MODEL.User
import java.util.*

class CurentEventModel
{
    var eventID:Int=0

    var objectID:Int=0

    var whoCreatedId:Int=0

    var discipline:Int=0

    var date: ObservableField<String> = ObservableField()

    var describe:ObservableField<String> = ObservableField()

    var longitude:Double=0.0

    var latitude:Double=0.0

    var name: ObservableField<String> = ObservableField()

    //0 -nie jet
    //1 jest uzytkownikem
    //2 jesst adminem
    var userStatus:Int=0

    var isComercial:Boolean=false
    var icoText:ObservableField<String> = ObservableField()

    var userList:MutableList<User> = arrayListOf()

}