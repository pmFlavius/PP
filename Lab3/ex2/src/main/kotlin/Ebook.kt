interface RegulaProcesare<T> {
    fun aplica(date: List<T>): List<T>
}


class FiltruPagini : RegulaProcesare<String> {
    override fun aplica(date: List<String>): List<String> {
        val rezultat = mutableListOf<String>()
        for (linie in date) {
            if (linie.trim().toIntOrNull() == null) {
                rezultat.add(linie)
            }
        }
        return rezultat
    }
}

class FiltruSpatii : RegulaProcesare<String> {
    override fun aplica(date: List<String>): List<String> {
        val rezultat = mutableListOf<String>()
        for (linie in date) {
            rezultat.add(linie.replace(Regex("\\s+"), " ").trim())
        }
        return rezultat
    }
}

class FiltruLiniiGoale : RegulaProcesare<String> {
    override fun aplica(date: List<String>): List<String> {
        val rezultat = mutableListOf<String>()
        var ultimaFostGoala = false

        for (linie in date) {
            val eGoala = linie.trim().isEmpty()

            if (!eGoala) {
                rezultat.add(linie)
                ultimaFostGoala = false
            } else if (!ultimaFostGoala) {
                rezultat.add("")
                ultimaFostGoala = true
            }
        }
        return rezultat
    }
}

fun main() {
    val textBrut = listOf(
        "Acesta   este   un       paragraf   din carte.",
        "",
        "",
        "",
        "   45   ",
        "",
        "Aici continua     cartea pe      pagina urmatoare.",
        "   ",
        "Final de   text."
    )

    val reguli = listOf(
        FiltruPagini(),
        FiltruSpatii(),
        FiltruLiniiGoale()
    )

    var textCurat = textBrut
    for (regula in reguli) {
        textCurat = regula.aplica(textCurat)
    }
    println()
    for (linie in textCurat) {
        println(linie)
    }
}