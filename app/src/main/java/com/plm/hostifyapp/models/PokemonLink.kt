package com.plm.hostifyapp.models

class PokemonLink (
    var url: String,
    var name: String
) {
    fun getId(): Int {
        return url.trim('/').split("/").last().toInt()
    }
}