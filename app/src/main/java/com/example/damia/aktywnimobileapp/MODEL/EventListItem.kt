package com.example.damia.aktywnimobileapp.MODEL

class EventListItem(name: String, description: String, data: String, sport: String) {
    var name: String = ""
    var description = ""
    var data = ""
    var sport = ""
    var eventID:Int=0
    init {
        this.name = name
        this.data = data
        this.description = description
        this.sport = sport
    }

}