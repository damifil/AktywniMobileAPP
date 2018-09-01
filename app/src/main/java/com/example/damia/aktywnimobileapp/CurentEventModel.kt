package com.example.damia.aktywnimobileapp

import android.databinding.ObservableField
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


    var isComercial:Boolean=false
    var icoText:ObservableField<String> = ObservableField()
}