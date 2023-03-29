package com.pict.pbl.medswift.data

data class User(
    val bldGrp : String ,
    val dateOfBirth : Long ,
    val email : String ,
    val firstName : String ,
    val gender : String ,
    val height : Float ,
    val lastName : String ,
    val phoneNumber : String ,
    val weight : Int
)

data class UserSymptom(
    val name : String ,
    val value : Int
) {
    constructor() : this( "" , 0 )
}

data class UserPrediction(
    val name : String ,
    val value : Float
) {
    constructor() : this( "" , 0.0f )
}

data class UserDiagnosis(
    val predictions : List<UserPrediction> ,
    val symptoms : List<UserSymptom>
)