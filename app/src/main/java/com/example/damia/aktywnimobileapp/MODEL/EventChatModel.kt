package com.example.damia.aktywnimobileapp.MODEL

class EventChatModel
{
    var chatList:MutableList<ChatValue> = arrayListOf()
    var eventIdOrUserName=""
    var lastchatListSize=0
    var userId=""
    var eventName=""
}

class ChatValue
{
    var nameUser=""
    var isMineName=false
    var chatmessage=""
    var messageId=0
    var date=""
}