package com.example.damia.aktywnimobileapp.MODEL

class EventChatModel
{
    var chatList:MutableList<ChatValue> = arrayListOf()
}

class ChatValue
{
    var nameUser=""
    var isMineName=false
    var chatmessage=""
}