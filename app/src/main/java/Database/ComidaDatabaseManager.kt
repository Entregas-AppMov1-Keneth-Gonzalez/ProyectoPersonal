package Database

import Entities.Comida
import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.ComidaDBManager

class ComidaDatabaseManager(context: Context) : ComidaDBManager {
    private val dbHelper = ComidaDatabaseHelper(context)

    override fun add(obj: Comida) {
        dbHelper.insertComida(obj)
    }

    override fun update(obj: Comida) {
        dbHelper.updateComida(obj)
    }

    override fun remove(id: String) {
        dbHelper.deleteComidaById(id)
    }

    override fun getAll(): List<Comida> {
        return dbHelper.getAllComidas()
    }

    override fun getByid(id: String): Comida? {
        return dbHelper.getComidaById(id)
    }

    override fun getByFullDescription(fullDescription: String): Comida? {
        return getAll().find { it.FullDescription == fullDescription }
    }
}
