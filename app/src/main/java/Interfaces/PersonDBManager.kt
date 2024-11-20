package cr.ac.utn.kenethgonzalez.proyectopersonal

import identities.Identifier

interface PersonDBManager {
    fun add (obj: Identifier)
    fun update (obj: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getByid(id: String): Identifier?
    fun getByFullDescription(fullDescription: String): Identifier?
}