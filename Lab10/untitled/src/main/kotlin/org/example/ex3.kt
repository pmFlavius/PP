package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking<Unit> {
    val coada = Channel<Int>() //asta functioneaza ca o coada
    val n = mutableListOf(10,25,100,67)

    val jobs = List(4){
        launch(Dispatchers.Default){
            for(i in coada){
                var rezultat = 0
                for(j in 1..i){
                    rezultat += j
                }
                println("Rezultatul este: " + rezultat)
            }
        }
    }

    for (valoare in n) {
        coada.send(valoare)
    }
    coada.close()

}