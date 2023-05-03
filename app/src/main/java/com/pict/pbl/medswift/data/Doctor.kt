package com.pict.pbl.medswift.data


data class Doctor(
    val degree : String ,
    val latitude : Float ,
    val longitude : Float ,
    val name : String ,
    val rating : Int ,
    val speciality : String
) {
    constructor() : this( "" , 0f , 0f , "" , 0 , "" )
}