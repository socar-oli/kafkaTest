package com.example.kafkatest

data class User(
    val name: String,
    val age: Int
) {
    constructor() : this("", 0)
}