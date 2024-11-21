package cr.ac.utn.kenethgonzalez.proyectopersonal

import Entities.Comida

interface ComidaDBManager {
    fun add (obj: Comida)
    fun update (obj: Comida)
    fun remove (id: String)
    fun getAll(): List<Comida>
    fun getByid(id: String): Comida?
    fun getByFullDescription(fullDescription: String): Comida?
}