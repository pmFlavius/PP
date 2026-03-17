import java.io.File

data class User(val nume:String, val id:Int)
data class Notita(val autor:User,val data:String,val continut:String,val nume:String)

interface Notite{
    fun salvareNotita(notita:Notita)
    fun incarcaNotita(nume:String):Notita?
    fun stergeNotita(nume:String): Boolean
    fun toateNotitele(): List<String>
}

class FileStorage(var folder:String): Notite{
    override fun salvareNotita(notita: Notita){
        val fisier = File("$folder/${notita.nume}.txt")

        val text = notita.autor.toString() + "\n" + notita.data + "\n" + notita.continut

        fisier.writeText(text)
    }
    override fun incarcaNotita(nume:String):Notita?{
        val fisier = File("$folder/${nume}.txt")

        if(!fisier.exists()){
            return null //pt null safety
        }

        val linii = fisier.readLines()
        val numeAutor = linii[0]
        val data = linii[1]
        var continut = ""
        var i = 2
        while(i < linii.size){
            continut+= linii[i]
            i++
        }

        //println(numeAutor)
        //println(data)
        //println(continut)
        //afisari intr o clasa separata ca sa respect principiul resp unice

        val autornou = User(numeAutor,0)
        return Notita(autornou,data,continut,nume)
    }

    override fun stergeNotita(nume: String): Boolean {
        val fisier = File("$folder/${nume}.txt")
        if(!fisier.exists()){
            return false
        }
        fisier.delete()
        return true
    }

    override fun toateNotitele(): List<String> {
        val folder = File(folder)
        val fisiere = folder.listFiles()
        var lista = mutableListOf<String>()
        for(fisier in fisiere)
            lista.add(fisier.name)
        return lista
    }


}

class AfisareNotite(val notite: Notite){
    public fun afisareNotite(){
        val lista = notite.toateNotitele()
        for(i in lista)
            println(i)
    }
    public fun stergeNotita(){
        println("Ce notita vrei sa stergi:")
        val nume = readln()

        if(notite.stergeNotita(nume)){
            println("S a sters notita" + nume)
        }
        else
            println("Nu s a sters notita" + nume)
    }
    public fun creeazaNotita(autor:User){
        println("Nume notita:")
        val nume = readln()

        println("Continutul dorit:")
        val continut = readln()

        println("La ce ora ai scris?")
        val data = readln()
        notite.salvareNotita(Notita(autor,data,continut,nume))
    }
    public fun incarcaNotita(){
        println("Nume notita:")
        val nume = readln()

        val notita = notite.incarcaNotita(nume)
        if(notita != null){
            println(notita.nume)
            println(notita.autor.toString())
            println(notita.data)
            println(notita.continut)
        }
        else
            println("NU s o gasit notita dorita!")
    }

}
fun main(args: Array<String>) {
    val userCurent = User("Student", 1)
    val f=File("notite_salvate")
    f.mkdir()
    val storage = FileStorage("notite_salvate")
    val manager = AfisareNotite(storage)
    while(true){
        println("===MENIU===")
        println("Alege optiunea dorita:")
        println("1) Incarca notita:")
        println("2) Afisare notita:")
        println("3) Creeare notita:")
        println("4) Stergere notita")
        println("0) Iesire")
        var op = readln()
        //when e ca switch ul
        when (op){
            "1" -> manager.incarcaNotita()
            "2" -> manager.afisareNotite()
            "3" -> manager.creeazaNotita(userCurent)
            "4" -> manager.stergeNotita()
            "0" -> return
            else -> println("OPTIUNE GRESITA")
        }
    }
}

