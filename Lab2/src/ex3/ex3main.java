import org.graalvm.polyglot.*;

public class ex3main
{
    private static int[] citireNr()
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result=polyglot.eval("python",
        "x=int(input(''))\n"+
            "y=int(input(''))\n"+
            "[x, y]"
                );

        int[] v=new int[2];
        v[0]=result.getArrayElement(0).asInt(); //numarul de aruncari
        v[1]=result.getArrayElement(1).asInt(); //sa se obtina de x ori
        polyglot.close();

        return v;
    }
    private static double calculProp(int[] v)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("python").putMember("arr", v);
        Value result = polyglot.eval("python",
                "import math\n" +
                        "n = arr[0]\n" +
                        "k = arr[1]\n" +
                        "p = 0.5\n" +
                        "suma = 0\n"+
                        "for i in range(k+1):\n"+
                        "   suma += math.comb(n, i) * (p**i) * ((1-p)**(n-i))\n" +
                        "suma"
        );

        double rezultat=result.asDouble();
        polyglot.close();
        return rezultat;
    }
    public static void main(String[] args)
    {
        int[] v=citireNr();
        System.out.println("Probabilitatea ca din " + v[0] + "aruncari sa se obtina pajura de cel mult "+v[1]+"ori este:"+calculProp(v));

    }
}
