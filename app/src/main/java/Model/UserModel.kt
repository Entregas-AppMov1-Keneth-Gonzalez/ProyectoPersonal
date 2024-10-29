package Model

import Data.UserMemoryManager
import Entities.User
import Interfaces.UIManager
import android.content.Context
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

class UserModel {
    private var udbManager: UIManager = UserMemoryManager
    private lateinit var _context: Context

    constructor(context: Context){
        _context = context
    }

    fun addUser(user: User){
        udbManager.addUser(user)
    }

    fun getUsers() = udbManager.getAllUser()

    fun getUser(id: String): User {
        var result = udbManager.getUserById(id)
        if(result == null){
            val message = R.string.UserDoesntExist.toString()
            throw Exception(message)
        }else{
            return result
        }
    }

    fun getUserNames(): List<String>{
        val names = mutableListOf<String>()
        udbManager.getAllUser().forEach{i-> names.add(i.fullName)}
        return names.toList()
    }

    fun removeUser(id: String){
        var result = udbManager.getUserById(id)
        if(result == null){
            val message = R.string.UserDoesntExist.toString()
            throw Exception(message)
        }else{
            udbManager.removeUser(id)
        }
    }

    fun updateUser(user: User){
        udbManager.updateUser(user)
    }

    fun getUserByFullName(fullName: String): User{
        var result = udbManager.getUserByFullName(fullName)
        if(result == null){
            val message = R.string.UserDoesntExist.toString()
            throw Exception(message)
        }else{
            return result
        }
    }
}