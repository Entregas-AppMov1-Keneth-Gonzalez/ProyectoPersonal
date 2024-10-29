package Data

import Entities.User
import Interfaces.UIManager
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

object UserMemoryManager: UIManager {
    private var userList = mutableListOf<User>()

    override fun addUser(user: User) {
        userList.add(user)
    }

    override fun updateUser(user: User) {
        removeUser(user.id)
        userList.add(user)
    }

    override fun removeUser(id: String) {
        userList.removeIf{it.id.trim() == id.trim()}
    }

    override fun getAllUser(): List<User> = userList.toList()

    override fun getUserById(id: String): User? {
        try{
            var result = userList.filter { (it.id == id) }
            if(!result.any()){
                throw Exception(R.string.UserDoesntExist.toString())
            }else{
                return result[0]
            }
        }catch (e: Exception){
            throw e
        }
    }

    override fun getUserByFullName(fullName: String): User? {
        try {
            var result = userList.filter { (it.fullName == fullName) }
            if(!result.any()){
                throw Exception(R.string.UserDoesntExist.toString())
            }else{
                return result[0]
            }
        }catch (e: Exception){
            throw e
        }
    }
}