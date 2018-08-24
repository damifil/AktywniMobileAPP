package com.example.damia.aktywnimobileapp.PRESENTER

import com.example.damia.aktywnimobileapp.MODEL.EventAddModel
import com.example.damia.aktywnimobileapp.VIEW.EventAddkFragment


class EventAddPresenter(context: EventAddkFragment)
{
    var model: EventAddModel
    val context2: EventAddkFragment
    init {
        this.context2 = context
        this.model = EventAddModel()
    }
}