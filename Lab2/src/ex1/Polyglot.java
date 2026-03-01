//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

//clasa principala - aplicatie JAVA
class Polyglot extends HT {
    //metoda privata pentru conversie low-case -> up-case folosind functia toupper() din R
    private static String ToUpper(String token){
        //construim un context care ne permite sa folosim elemente din R
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei funcitiei R, toupper(String)
        //pentru aexecuta instructiunea I din limbajul X, folosim functia graalvm polyglot.eval("X", "I");
        Value result = polyglot.eval("python",
                "'"+token+"'.upper()"
        );
        //utilizam metoda asString() din variabila incarcata cu output-ul executiei pentru a mapa valoarea generica la un String
        String resultString = result.asString();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultString;
    }

    //metoda privata pentru evaluarea unei sume de control simple a literelor unui text ASCII, folosind PYTHON
    private static int SumCRC(String token){
        //construim un context care ne permite sa folosim elemente din PYTHON
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei functiei PYTHON, sum()
        //avem voie sa inlocuim anumite elemente din scriptul pe care il construim spre evaluare, aici token provine din JAVA, dar va fi interpretat de PYTHON
        Value result = polyglot.eval("python",
        "sum=0\n" +
                "sir='"+token+"'[1:-1]\n"+
                "for x in sir:\n" +
                "   sum+=ord(x)**3+5*ord(x)**2+22\n" +
                "sum"
        );
        //utilizam metoda asInt() din variabila incarcata cu output-ul executiei, pentru a mapa valoarea generica la un Int
        int resultInt = result.asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultInt;
    }
    private static void GenerateRand(int [] numere)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result = polyglot.eval("python",
                "import random\n"+
                        "[random.randint(1,100) for i in range(20)]"
        );

        for (int i = 0; i < 20; i++) {
            numere[i] = result.getArrayElement(i).asInt();
        }

        polyglot.close();

    }
    private static double medieAritmetica(int[] numere)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("python").putMember("numere_java", numere);
        Value result = polyglot.eval("python",
                "arr=list(numere_java)\n"+
                        "arr.sort()\n"+
                        "t=int(0.2*len(arr))\n"+
                        "arr=arr[t:-t]\n"+
                        "sum(arr)/len(arr)"
        );

        double rezultat=result.asDouble();
        polyglot.close();

        return rezultat;
    }
    private static void afisare(int[] numere)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        polyglot.getBindings("js").putMember("numere_java", numere);
        Value result = polyglot.eval("js",
                "const arr=numere_java;\n"+
                        "for(let i=0;i<arr.length;i++)\n"+
                        "   console.log(arr[i]);"
        );

        polyglot.close();
    }
    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();
        //construim un array de string-uri, folosind cuvinte din pagina web:  https://chrisseaton.com/truffleruby/tenthings/
        Value array = polyglot.eval("js", "[\"If\",\"we\",\"run\",\"the\",\"java\",\"command\",\"included\",\"in\",\"GraalVM\",\"we\",\"will\",\"be\",\"automatically\",\"using\",\"the\",\"Graal\",\"JIT\",\"compiler\",\"no\",\"extra\",\"configuration\",\"is\",\"needed\",\"I\",\"will\",\"use\",\"the\",\"time\",\"command\",\"to\",\"get\",\"the\",\"real\",\"wall\",\"clock\",\"elapsed\",\"time\",\"it\",\"takes\",\"to\",\"run\",\"the\",\"entire\",\"program\",\"from\",\"start\",\"to\",\"finish\",\"rather\",\"than\",\"setting\",\"up\",\"a\",\"complicated\",\"micro\",\"benchmark\",\"and\",\"I\",\"will\",\"use\",\"a\",\"large\",\"input\",\"so\",\"that\",\"we\",\"arent\",\"quibbling\",\"about\",\"a\",\"few\",\"seconds\",\"here\",\"or\",\"there\",\"The\",\"large.txt\",\"file\",\"is\",\"150\",\"MB\"];");
        //pentru fiecare cuvant, convertim la upcase folosind R si calculam suma de control folosind PYTHON

        HT ht=new HT();
        for (int i = 0; i < array.getArraySize();i++){
            String element = array.getArrayElement(i).asString();
            String upper = ToUpper(element);
            int crc = SumCRC(upper);
            System.out.println(upper + " -> " + crc);
            ht.inserare(crc,upper);
        }
        /*System.out.println("Urmeaza cerinta din ex3:");
        int[] numere=new int[20];
        GenerateRand(numere);
        afisare(numere);
        System.out.println("Media aritmetica a numerelor generate(fara primele 20% si ultimele 20%) este: " + medieAritmetica(numere));*/

        ht.afisareColiziuni();
        // inchidem contextul Polyglot
        polyglot.close();
    }
}
