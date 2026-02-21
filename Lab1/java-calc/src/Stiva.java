import java.lang.Exception;
import java.util.concurrent.ExecutionException;

public class Stiva<T>
{
    private class Nod
    {
        T data;
        Nod succ;
        Nod(T data,Nod succ)
        {
            this.data=data;
            this.succ=succ;
        }
    }
    private Nod cap;
    public Stiva()
    {
        this.cap=null;
    }
    public boolean isEmpty()
    {
        return this.cap==null;
    }
    public void push(T val)
    {
        cap=new Nod(val,cap);
    }
    public T top() throws Exception
    {
        if(!isEmpty())
            return this.cap.data;
        else
            throw new Exception("Stiva goala");
    }
    public void pop() throws Exception
    {
        if(isEmpty())
            throw new Exception("Stiva goala");
        else
            cap=cap.succ;
    }
}
