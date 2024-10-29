package Interfaces

import Entities.Food

interface FIManager {
    fun addFood (food: Food)
    fun updateFood(food: Food)
    fun removeFood(id: String)
    fun getAllFood(): List<Food>
    fun getFoodById(id: String): Food?
}