import kotlin.math.sqrt

fun exercitiul1() {
    println("Exercitiul 1: ")

    val lista = listOf(1, 21, 75, 39, 7, 2, 35, 3, 31, 7, 8)

    val filtrate = lista.filter { it >= 5 }
    println("Dupa filtrare: $filtrate")

    val perechi = filtrate.chunked(2);
    println("Perechi: $perechi")

    val produse = perechi.map { pereche -> pereche[0] * pereche[1] }
    println("Dupa inmultire: $produse")

    val suma = produse.reduce { acc, i -> acc + i }
    println("Suma finala: $suma")
}


fun criptareCaesar(cuvant: String, offset: Int): String {
    return cuvant.map { litera ->
        when {
            litera in 'a'..'z' -> 'a' + (litera - 'a' + offset) % 26
            litera in 'A'..'Z' -> 'A' + (litera - 'A' + offset) % 26
            else -> litera
        }
    }.joinToString("")
}

fun exercitiul2() {
    println("\nExercitiul 2:")

    val fisier = java.io.File("test.txt")
    fisier.writeText("Hello world salut sunt FLavius alabala")

    val offset = 3
    val continut = fisier.readText()
    println("Text original: $continut")

    val rezultat = continut.split(" ")
        .map { cuvant ->
            if (cuvant.length in 4..7)
                criptareCaesar(cuvant, offset)
            else
                cuvant
        }
        .joinToString(separator = " ")

    println("Text criptat (offset=$offset): $rezultat")
}

fun exercitiul3() {
    println("\nExercitiul 3:")

    val puncte = listOf(
        Pair(0.0, 0.0),
        Pair(1.0, 0.0),
        Pair(1.0, 1.0),
        Pair(0.0, 1.0)
    )

    val puncteDecalate = puncte.drop(1) + puncte.first()

    val perimetru = puncte.zip(puncteDecalate)
        .map { (p1, p2) ->

            val dx = p2.first - p1.first
            val dy = p2.second - p1.second

            sqrt(dx * dx + dy * dy)
        }
        .sum()

    println("Perimetru: $perimetru")
}


fun String.toPascalCase(): String =
    this.split(" ").map { it.replaceFirstChar { c -> c.uppercaseChar() } }.joinToString(separator = " ")

class MapFunctor<K, V>(val map: MutableMap<K, V>) {

    fun map(f: (V) -> V): MapFunctor<K, V> {
        val transformat = map.entries.map { it.key to f(it.value) }.toMap()
        return MapFunctor(transformat.toMutableMap())
    }
}

fun exercitiul4() {
    println("\nExercitiul 4: ")

    val harta = mutableMapOf(
        1 to "ana are mere",
        2 to "george merge la scoala",
        3 to "o zi frumoasa"
    )

    println("Initial: $harta")

    val rezultat = MapFunctor(harta)
        .map { valoare -> "Test $valoare" }
        .map { valoare -> valoare.toPascalCase() }

    println("Final: ${rezultat.map}")
}


fun main() {
    exercitiul1()
    exercitiul2()
    exercitiul3()
    exercitiul4()
}
