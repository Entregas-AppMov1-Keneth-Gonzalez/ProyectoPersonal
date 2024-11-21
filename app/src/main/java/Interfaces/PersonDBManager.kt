package cr.ac.utn.kenethgonzalez.proyectopersonal

import Entities.Person

interface PersonDBManager {
    fun add (obj: Person)
    fun update (obj: Person)
    fun remove (id: String)
    fun getAll(): List<Person>
    fun getByid(id: String): Person?
    fun getByFullDescription(fullDescription: String): Person?
}