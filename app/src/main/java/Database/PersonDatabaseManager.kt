package Database

import Entities.Person
import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.PersonDBManager

class PersonDatabaseManager(context: Context) : PersonDBManager {
    private val dbHelper = PersonDatabaseHelper(context)

    override fun add(obj: Person) {
        dbHelper.insertPerson(obj)
    }

    override fun update(obj: Person) {
        dbHelper.updatePerson(obj)
    }

    override fun remove(id: String) {
        dbHelper.deletePersonById(id)
    }

    override fun getAll(): List<Person> {
        return dbHelper.getAllPersons()
    }

    override fun getByid(id: String): Person? {
        return dbHelper.getPersonById(id)
    }

    override fun getByFullDescription(fullDescription: String): Person? {
        return getAll().find { it.FullDescription == fullDescription }
    }
}
