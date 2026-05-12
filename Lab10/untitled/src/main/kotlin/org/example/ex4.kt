package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.File

fun main(args: Array<String>) = runBlocking<Unit> {
    val coada = Channel<String>()
    val fisiere = if (args.isNotEmpty()) args.toList() else listOf("fisier_test.txt")

    val jobs = List(3){
        launch(Dispatchers.IO) {
            for(f in coada){
                try{
                    val continut = File(f).readText()
                    println("CONTINUTUL FISIERULUI : " + f)
                    println(continut)
                }
                catch(e:Exception){
                    println("Nu s a putut deschide fisieru")
                }
            }
        }
    }

    for(f in fisiere)
        coada.send(f)

    coada.close()
}