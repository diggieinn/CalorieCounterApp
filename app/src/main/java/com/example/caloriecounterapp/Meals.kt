package com.example.caloriecounterapp

class Meals {
    var id : Int = 0
    var name : String = ""
    var calories : Int = 0


    constructor(name: String, calories: Int) {
        this.name = name
        this.calories = calories
    }


    constructor() {

    }
}