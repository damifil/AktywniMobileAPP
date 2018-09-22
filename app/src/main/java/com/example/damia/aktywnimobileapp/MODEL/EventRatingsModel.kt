package com.example.damia.aktywnimobileapp.MODEL

class EventRatingsModel
{
  var comentList:ArrayList<Comment> = arrayListOf()
}

class Comment
{
    var userIDRated=0
    var eventID=0
    var Rate=-1
    var describe=""
    var isAdded=false
}