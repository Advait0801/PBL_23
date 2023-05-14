package com.pict.pbl.medswift.data

import java.util.Date

data class User(
    var bldGrp : String ,
    var dateOfBirth : Date ,
    var email : String ,
    var firstName : String ,
    var gender : String ,
    var height : Float ,
    var lastName : String ,
    var phoneNumber : String ,
    var weight : Int
) {

    constructor() : this( "" , Date() , "" , "" , "" , 0.0f , "" , "" , 0 )

    fun validateDetails() : Boolean {
        return bldGrp.isNotEmpty() &&
                email.isNotEmpty() &&
                firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                weight > 0 &&
                height > 0.0f &&
                gender.isNotEmpty() &&
                (phoneNumber.isNotEmpty() && phoneNumber.length == 10 )
    }

}

data class UserPrescription(
    var courseLength : Int ,
    var instructions : String ,
    var name : String ,
    var startTime : Date ,
    var times : List<Int>
) {
    constructor() : this( 0 , "" , "" , Date() , listOf( 0 ) )
}

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
    val symptoms : List<UserSymptom> ,
    val lat : Double ,
    val lng : Double ,
    val time : Date
)