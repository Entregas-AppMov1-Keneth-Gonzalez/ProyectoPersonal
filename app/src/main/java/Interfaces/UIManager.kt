package Interfaces

import Entities.User

interface UIManager {
    fun addUser (user: User)
    fun updateUser(user: User)
    fun removeUser(id: String)
    fun getAllUser(): List<User>
    fun getUserById(id: String): User?
    fun getUserByFullName(fullName: String): User?
}