package com.example.damia.aktywnimobileapp.MODEL

class EventListItem(name: String, description: String, data: String, sport: String) {
    var name: String = ""
    var description = ""
    var data = ""
    var sport = ""
    var eventID:Int=0
    var adminLogin=""
    init {
        this.name = name
        this.data = data
        this.description = description
        this.sport = sport
    }


    fun convertFromEvent(event:Event)
    {
        name=event.eventName
        description=event.description
        data=event.date.toString()
        sport=event.typeOfSport
        eventID=event.eventID
    }

}