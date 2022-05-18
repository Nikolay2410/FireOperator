package com.example.fireoperator.ui.tariff

//class Tariff (_name: String, _description: String, _cost: Double){
//    val name: String = _name
//    val description = _description
//    val cost = _cost
//
//    override fun toString(): String {
//        return "Tariff: name: $name, cost: $cost, description: $description"
//    }
//}

data class Tariff(var id: String? = null, var name: String? = null, var description: String? = null, var cost: Double? = null)