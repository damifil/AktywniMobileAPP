package com.example.damia.aktywnimobileapp.PRESENTER

import com.example.damia.aktywnimobileapp.MODEL.EventAddModel
import com.example.damia.aktywnimobileapp.MODEL.EventChatModel
import com.example.damia.aktywnimobileapp.VIEW.EventAddkFragment
import com.example.damia.aktywnimobileapp.VIEW.EventChatFragment

class EventChatPresenter(context: EventChatFragment)
{
    var model: EventChatModel
    val context2: EventChatFragment
    init {
        this.context2 = context
        this.model = EventChatModel()
    }
}