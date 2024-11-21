package cr.ac.utn.kenethgonzalez.proyectopersonal

import cr.ac.utn.kenethgonzalez.proyectopersonal.ComidaDBManager
import Entities.Comida

object ComidaMemoryManager: ComidaDBManager {
    private var comidaList = mutableListOf<Comida>()

    override fun add (obj: Comida){
        comidaList.add(obj)
    }
    override fun update (obj: Comida){
        remove(obj.Id)
        comidaList.add(obj)

    }
    override fun remove (id: String){
        comidaList.removeIf { it.Id.trim() == id.trim() }
    }

    fun remove (obj: Comida){
        comidaList.remove(obj)
    }

    override fun getAll(): List<Comida> = comidaList.toList()

    override fun getByid(id: String): Comida? {
        try {
            var result = comidaList.filter { (it.Id) == id }
            return if(!result.any()) null else result[0]
        }catch (e: Exception){
            throw e
        }
    }

    override fun getByFullDescription(fullDescription: String): Comida? {
        try {
            var result = comidaList.filter { (it.FullDescription) == fullDescription }
            return if(!result.any()) null else result[0]
        }catch (e: Exception){
            throw e
        }
    }
}