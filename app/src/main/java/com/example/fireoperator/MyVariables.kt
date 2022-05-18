package com.example.fireoperator

object MyVariables {
    private var user: String = ""

    fun getUser(): String {
        return user
    }
    fun setUser(newUser: String) {
        user = newUser
    }
}