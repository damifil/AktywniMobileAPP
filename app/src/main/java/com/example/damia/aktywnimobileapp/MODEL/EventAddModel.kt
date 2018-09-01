package com.example.damia.aktywnimobileapp.MODEL

class EventAddModel( var longitude:Double=0.0,
                     var latitude:Double=0.0,
                     var description:String="",
                     var eventName:String="",
                     var date:String="Data:")
{
    val Sports: Array<SportObject> = arrayOf(
            SportObject("Baseball","\uf433"),
            SportObject("Koszykówka","\uf434"),
            SportObject("Kręgle","\uf436"),
            SportObject("Footbol amerykański","\uf44e"),
            SportObject("Piłka nożna","\uf1e3"),
            SportObject("Golf","\uf450"),
            SportObject("Hokej","\uf453"),
            SportObject("ping-pong","\uf45d"),
            SportObject("Siatkówka","\uf45f"),
            SportObject("Pływanie","\uf5c4"),
            SportObject("Rowery","\uf206"),
            SportObject("Jeździectwo","\uf3f7"),
            SportObject("Biegi","\uf554")
    )
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
class SportObject(val nam:String, val icoText:String)
{
    val name:String=nam
    val code=icoText
    var isClick=false
}
