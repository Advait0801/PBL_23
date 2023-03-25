package com.pict.pbl.medswift.data

import kotlinx.serialization.Transient

data class AnalyzeSymptom( val name : String , var value : String )

@kotlinx.serialization.Serializable
enum class SymptomChoiceType( val value : String ) {
    categorical( "categorical" ),
    double( "double" ),
    integer( "integer" )
}

@kotlinx.serialization.Serializable
data class SymptomChoice(
    val text: String ,
    val laytext: String ,
    val value : Int ,
    val relatedanswertag : String? = null
)

@kotlinx.serialization.Serializable
data class Symptom(
    val text : String,
    val laytext : String,
    val name : String,
    val type : SymptomChoiceType,
    val min : Double? = null,
    val max : Double? = null,
    val default : Double,
    val category : String,
    val alias : String,
    val wiki : String,
    val wiki2 : String,
    val wiki3 : String,
    val wiki4 : String,
    val subcategory1 : String ,
    val subcategory2 : String ,
    val subcategory3 : String ,
    val subcategory4 : String ,
    val IsPatientProvided : Boolean,
    val step : Double? = null,
    val choices : Array<SymptomChoice>? = null ,
    @Transient var isUserSelected : Boolean = false
)