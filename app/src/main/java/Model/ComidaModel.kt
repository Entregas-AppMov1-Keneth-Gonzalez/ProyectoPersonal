package model

import Database.ComidaDatabaseManager
import Database.PersonDatabaseManager
import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.R
import Entities.Comida
import cr.ac.utn.kenethgonzalez.proyectopersonal.ComidaDBManager
import cr.ac.utn.kenethgonzalez.proyectopersonal.ComidaMemoryManager
import cr.ac.utn.kenethgonzalez.proyectopersonal.PersonDBManager

class ComidaModel(context: Context) {
    //private var comidadbManager: ComidaDBManager = ComidaMemoryManager
    private var comidadbManager: ComidaDBManager = ComidaDatabaseManager(context)
    private val _context: Context = context

    fun addComida(comida: Comida) {
        comidadbManager.add(comida)
    }

    fun getComidas(): List<Comida> {
        return comidadbManager.getAll().filterIsInstance<Comida>()
    }

    fun getComida(id: String): Comida? {
        val result = comidadbManager.getByid(id) as? Comida
        if (result == null) {
            val message = _context.getString(R.string.food_NotFound)
            throw Exception(message)
        }
        return result
    }

    fun removeComida(id: String) {
        val result = comidadbManager.getByid(id) as? Comida
        if (result == null) {
            val message = _context.getString(R.string.food_NotFound)
            throw Exception(message)
        }
        comidadbManager.remove(id)
    }

    fun updateComida(comida: Comida) {
        comidadbManager.update(comida)
    }

    fun getComidaByFullDescription(fullDescription: String): Comida? {
        val result = comidadbManager.getByFullDescription(fullDescription) as? Comida
        if (result == null) {
            val message = _context.getString(R.string.food_NotFound)
            throw Exception(message)
        }
        return result
    }
}
