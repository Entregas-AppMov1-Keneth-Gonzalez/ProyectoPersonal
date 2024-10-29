package Data

import Entities.Food
import Interfaces.FIManager
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

object FoodMemoryManager: FIManager {
    private var foodList = mutableListOf<Food>()

    override fun addFood(food: Food) {
        foodList.add(food)
    }

    override fun updateFood(food: Food) {
        removeFood(food.idFood)
        foodList.add(food)
    }

    override fun removeFood(id: String) {
        foodList.removeIf{it.idFood.trim() == id.trim()}
    }

    override fun getAllFood(): List<Food> = foodList.toList()

    override fun getFoodById(id: String): Food? {
        var result = foodList.filter { (it.idFood == id) }
        try {
            if(!result.any()){
                throw Exception(R.string.FoodDoesntExist.toString())
            }else{
                return result[0]
            }
        }catch (e: Exception){
            throw e
        }
    }
}