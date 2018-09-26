package com.example.damia.aktywnimobileapp.MODEL

class EventSearchModel
{
    val Sports: Array<SportObject> = arrayOf(
            SportObject("Baseball","\uf433",2),
            SportObject("Koszykówka","\uf434",3),
            SportObject("Kręgle","\uf436",4),
            SportObject("Footbol amerykański","\uf44e",5),
            SportObject("Piłka nożna","\uf1e3",6),
            SportObject("Golf","\uf450",7),
            SportObject("Hokej","\uf453",8),
            SportObject("ping-pong","\uf45d",9),
            SportObject("Siatkówka","\uf45f",10),
            SportObject("Pływanie","\uf5c4",11),
            SportObject("Rowery","\uf206",12),
            SportObject("Biegi","\uf554",14)
    )
    var eventId=0

    var km=5
    var eventList: ArrayList<EventListItem> = ArrayList()

}