package com.example.caloriecounterapp


class User {
    //user variable
    var id : Int = 0
    var name : String = ""
    var age : Int = 0
    var height : Int = 0
    var weight : Int = 0
    var targetWeight : Int = 0
    var weightLossTarget : Int = 0
    var gender : String = ""

    //constructor

    constructor(name : String, age : Int, height : Int, weight : Int, gender : String, targetWeight : Int, weightLossTarget : Int){
        this.targetWeight = targetWeight
        this.weightLossTarget = weightLossTarget
        this.name = name
        this.age = age
        this.height = height
        this.weight = weight
        this.gender = gender
    }

        //empty constructor
    constructor(){

    }

}
