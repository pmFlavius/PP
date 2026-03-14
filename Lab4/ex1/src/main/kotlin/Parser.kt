import org.jsoup.Jsoup

interface Parser {
    fun parse(url: String, content: String): String
}

class JsonParser : Parser {
    override fun parse(url: String, content: String): String {
        val doc = Jsoup.parse(content)
        val titlu = doc.title()
        val caractere = content.length

        return """
        {
            "sursa": "$url",
            "detalii": {
                "titlu_pagina": "$titlu",
                "total_caractere": $caractere
            },
        }
        """.trimIndent()
    }
}

class Crawler(private var url: String) {
    fun getResource(): String {
        return Jsoup.connect(url).ignoreContentType(true).execute().body()
    }

    fun processContent(content: String, parser: Parser) {
        val jsonRezultat = parser.parse(url, content)
        println(jsonRezultat)
        java.io.File("output.json").writeText(jsonRezultat)
    }
}

fun main(args: Array<String>) {
    val targetUrl = "https://jsonplaceholder.typicode.com/todos/1"
    val crawler = Crawler(targetUrl)
    val myParser = JsonParser()

    val content = crawler.getResource()

    if (content != null) {
        crawler.processContent(content, myParser)
    } else {
        println("probleme la preluarea continutului")
    }
}