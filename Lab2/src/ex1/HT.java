import java.util.*;

public class HT {
    private class Nod
    {
        int data;
        String sir;
        Nod(int data,String sir)
        {
            this.data=data;
            this.sir=sir;
        }
    }

    private final int dimensiune=100;
    private LinkedList<Nod>[] tabel;

    public HT()
    {
        tabel=new LinkedList[dimensiune];
        for(int i=0;i<dimensiune;i++)
        {
            tabel[i]=new LinkedList<>();
        }
    }

    private int hash(int sum)
    {
        return sum%dimensiune;
    }


    public void inserare(int sum,String cuvant)
    {
        int h=hash(sum);
        for(int i=0;i<tabel[h].size();i++)
        {
            if(tabel[h].get(i).sir.equals(cuvant))
                return;
        }
        tabel[h].add(new Nod(sum,cuvant));
    }

    public void afisareColiziuni()
    {
        for(int i=0;i<dimensiune;i++)
        {
            for (int j = 0; j < tabel[i].size(); j++)
            {

                int crcCurent = tabel[i].get(j).data;
                String cuvantCurent = tabel[i].get(j).sir;

                String duplicate = "";
                boolean amGasitColiziune = false;

                for (int k = j + 1; k < tabel[i].size(); k++)
                {
                    if (tabel[i].get(k).data == crcCurent)
                    {
                        duplicate += ", " + tabel[i].get(k).sir;
                        amGasitColiziune = true;
                    }
                }
                if (amGasitColiziune)
                {
                    System.out.println("Suma CRC " + crcCurent + ": " + cuvantCurent + duplicate);
                    i++;
                }
            }
        }
    }
}
