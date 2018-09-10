package com.example.damia.aktywnimobileapp.MODEL

class EventChatModel
{
    var chatList:MutableList<ChatValue> = arrayListOf()
    var eventId=0
    var lastchatListSize=0
}

class ChatValue
{
    var nameUser=""
    var isMineName=false
    var chatmessage=""
    var messageId=0
    var date=""
}