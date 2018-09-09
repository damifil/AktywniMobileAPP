package com.example.damia.aktywnimobileapp.MODEL

class UserProfilModel
{
    var profilName:String=""
    var userRating:Double=0.0
    var coments:ArrayList<Coment> = ArrayList()
    var userID:Int=0
    var userDescribe=""
    var isFriend:Boolean=false
    var isAcceptedFriend=false
    var friendList:ArrayList<User> = ArrayList()
}

class Coment()
{
    var from=""
    var content=""
}