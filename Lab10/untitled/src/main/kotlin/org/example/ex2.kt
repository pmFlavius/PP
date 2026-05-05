package org.example
import kotlinx.coroutines.*

fun main() = runBlocking<Unit>{
    val arr = mutableListOf<Int>(5,2,8,10,1,9,44)
    val alpha = 10

    println("Vectorul initial: " + arr)

    val etapa1 = async(Dispatchers.Default) {
        for(i in arr.indices) {
            arr[i] *= alpha
        }
        println("Etapa 1: Fiecare element din vecctor a fost inmultit cu alpha = " + alpha)
        arr
    }

    val etapa2 = async(Dispatchers.Default) {
        val vect = etapa1.await()
        vect.sort()
        println("Etapa 2: Vectorul a fost sortat:")
        vect
    }

    val etapa3 = async(Dispatchers.Default) {
        val vect = etapa2.await()
        println("Etapa 3: Afisare vector final")
        println(vect)
    }

    etapa3.await()
}

