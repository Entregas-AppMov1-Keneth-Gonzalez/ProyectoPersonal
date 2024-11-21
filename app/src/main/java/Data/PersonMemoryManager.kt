package cr.ac.utn.kenethgonzalez.proyectopersonal

import cr.ac.utn.kenethgonzalez.proyectopersonal.PersonDBManager
import Entities.Person

object PersonMemoryManager: PersonDBManager {
    private var personList = mutableListOf<Person>()

    override fun add (obj: Person){
        personList.add(obj)
    }
    override fun update (obj: Person){
        remove(obj.Id)
        personList.add(obj)

    }
    override fun remove (id: String){
        personList.removeIf { it.Id.trim() == id.trim() }
    }

    fun remove (obj: Person){
        personList.remove(obj)
    }

    override fun getAll(): List<Person> = personList.toList()

    override fun getByid(id: String): Person? {
        try {
            var result = personList.filter { (it.Id) == id }
            return if(!result.any()) null else result[0]
        }catch (e: Exception){
            throw e
        }
    }

    override fun getByFullDescription(fullDescription: String): Person? {
        try {
            var result = personList.filter { (it.FullDescription) == fullDescription }
            return if(!result.any()) null else result[0]
        }catch (e: Exception){
            throw e
        }
    }
}