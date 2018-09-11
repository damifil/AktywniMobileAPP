package com.example.damia.aktywnimobileapp.MODEL

class EventAddModel( var longitude:Double=0.0,
                     var latitude:Double=0.0,
                     var description:String="",
                     var eventName:String="",
                     var date:String="Data:")
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
            SportObject("Jeździectwo","\uf3f7",13),
            SportObject("Biegi","\uf554",14)
    )
    var eventId=0


fun isCorrectData():Boolean
{
    if(longitude>0.0 && description!="" && eventName!="" && date!="" && date!="Data:")
    {
        return true
    }
    return false
}
    fun resetData()
    {
         longitude=0.0
         latitude=0.0
         description=""
         eventName=""
         date="Data:"
    }

}
class SportObject(val nam:String, val icoText:String,val id:Int)
{
    val idEvent:Int=id
    val name:String=nam
    val code=icoText
    var isClick=false
}
