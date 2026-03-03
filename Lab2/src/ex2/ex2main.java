import org.graalvm.polyglot.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class ex2main {
    public static void main(String[] args) throws Exception
    {
        double[] x = new double[10];
        double[] y = new double[10];

        try (BufferedReader br = new BufferedReader(new FileReader("fisier.txt")))
        {
            String linie;
            int i = 0;
            while ((linie = br.readLine()) != null && i < 10) {
                String[] val = linie.split(",");
                x[i] = Double.parseDouble(val[0].trim());
                y[i] = Double.parseDouble(val[1].trim());
                i++;
            }
        } catch (Exception e)
        {
            System.out.println("Eroare la citirea fișierului");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Nume imagine: "); String nume = sc.next();
        System.out.print("Cale salvare: "); String cale = sc.next();
        System.out.print("Culoare puncte: "); String cPct = sc.next();
        System.out.print("Culoare linie: "); String cLin = sc.next();

        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        polyglot.getBindings("python").putMember("x", x);
        polyglot.getBindings("python").putMember("y", y);

        Value rez = polyglot.eval("python",
                "n = len(x)\n" +
                        "m = (n*sum(x[i]*y[i] for i in range(n)) - sum(x)*sum(y)) / (n*sum(i**2 for i in x) - sum(x)**2)\n" +
                        "c = (sum(y) - m*sum(x)) / n\n" +
                        "[m, c]"
        );
        double m = rez.getArrayElement(0).asDouble();
        double c = rez.getArrayElement(1).asDouble();
        polyglot.close();

        int size = 500;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);

        double maxX = x[0];
        double maxY = y[0];

        for (int j = 1; j < x.length; j++)
        {
            if (x[j] > maxX)
            {
                maxX = x[j];
            }
            if (y[j] > maxY)
            {
                maxY = y[j];
            }
        }

        double scaleX = (size - 50) / maxX;
        double scaleY = (size - 50) / maxY;

        g.setColor(getColor(cLin));
        g.drawLine(0, size - (int)(c * scaleY), size, size - (int)((m * (size/scaleX) + c) * scaleY));

        g.setColor(getColor(cPct));

        for (int i = 0; i < x.length; i++)
        {
            if (x[i] != 0.0 || y[i] != 0.0)
            {
                g.fillOval((int)(x[i] * scaleX), size - (int)(y[i] * scaleY), 10, 10);
            }
        }

        File fisier = new File(cale, nume);
        ImageIO.write(img, "png", fisier);

        if (Desktop.isDesktopSupported() && fisier.exists())
        {
            Desktop.getDesktop().open(fisier);
        }
    }

    private static Color getColor(String nume) {
        if (nume.equalsIgnoreCase("red")) return Color.RED;
        if (nume.equalsIgnoreCase("blue")) return Color.BLUE;
        if (nume.equalsIgnoreCase("green")) return Color.GREEN;
        return Color.BLACK;
    }
}