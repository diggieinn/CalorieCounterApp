package com.example.caloriecounterapp

class Meals {
    var id : Int = 0
    var name : String = ""
    var calories : Int = 0
    var protein : Int = 0
    var carbs : Int = 0
    var fat : Int = 0
    var date : String = ""
    var category : String = ""
    var cupOfwater : Int = 0



    constructor(name : String, calories : Int, protein : Int, carbs : Int, fat : Int, date : String, category : String, cupOfwater : Int){
        this.name = name
        this.calories = calories
        this.protein = protein
        this.carbs = carbs
        this.fat = fat
        this.date = date
        this.category = category
        this.cupOfwater = cupOfwater
    }



    constructor() {

    }




}