package model

import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.R
import Entities.Person
import cr.ac.utn.kenethgonzalez.proyectopersonal.PersonDBManager
import cr.ac.utn.kenethgonzalez.proyectopersonal.PersonMemoryManager

class PersonModel(context: Context) {
    private var persondbManager: PersonDBManager = PersonMemoryManager
    private val _context: Context = context

    fun addPerson(person: Person) {
        persondbManager.add(person)
    }

    fun getPersons(): List<Person> {
        return persondbManager.getAll().filterIsInstance<Person>()
    }

    fun getPerson(id: String): Person? {
        val result = persondbManager.getByid(id) as? Person
        if (result == null) {
            val message = _context.getString(R.string.person_NotFound)
            throw Exception(message)
        }
        return result
    }

    fun removePerson(id: String) {
        val result = persondbManager.getByid(id) as? Person
        if (result == null) {
            val message = _context.getString(R.string.person_NotFound)
            throw Exception(message)
        }
        persondbManager.remove(id)
    }

    fun updatePerson(person: Person) {
        persondbManager.update(person)
    }

    fun getPersonByFullDescription(fullDescription: String): Person? {
        val result = persondbManager.getByFullDescription(fullDescription) as? Person
        if (result == null) {
            val message = _context.getString(R.string.person_NotFound)
            throw Exception(message)
        }
        return result
    }
}