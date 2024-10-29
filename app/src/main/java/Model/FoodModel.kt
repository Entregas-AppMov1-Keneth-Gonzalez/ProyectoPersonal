package Model

import Data.FoodMemoryManager
import Entities.Food
import Interfaces.FIManager
import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

class FoodModel {
    private var fdbManager: FIManager = FoodMemoryManager
    private lateinit var _context: Context

    constructor(context: Context){
        _context = context
    }

    fun addFood(food: Food){
        fdbManager.addFood(food)
    }

    fun getFoods() = fdbManager.getAllFood()

    fun getFood(id: String): Food{
        var result = fdbManager.getFoodById(id)
        if(result == null){
            val message = R.string.FoodDoesntExist.toString()
            throw Exception(message)
        }else{
            return result
        }
    }

    fun removeFood(id: String){
        var result = fdbManager.getFoodById(id)
        if(result == null){
            val message = R.string.FoodDoesntExist.toString()
            throw Exception(message)
        }else{
            fdbManager.removeFood(id)
        }
    }

    fun updateFood(food: Food){
        fdbManager.updateFood(food)
    }
}