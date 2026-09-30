package com.example.horoscopo

data class Horoscope (
    val id: String, // Unico e inmutable, llama a la Api y fav
    val name: Int,
    val date: Int,
    val sign: Int
) {

}