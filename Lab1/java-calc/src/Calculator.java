import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" = "),
            new JButton(" C "),
            new JButton(" ( "),
            new JButton(" ) ")
    };

    String oper_values[] = {"+", "-", "*", "/", "=", "","(",")"};

    char operator;

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setSize(240, 250);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static  int prioritate(char c)
    {
        if(c=='+' || c=='-')
            return 1;
        else if(c=='*' || c=='/')
            return 2;
        return 0;
    }
    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i=0;i<10;i++)
            buttonpanel.add(digits[i]);

        for (int i=0;i<8;i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i=0;i<10;i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i=0;i<8;i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 5)
                        area.setText("");
                    else
                    if (finalI == 4) {
                        String text,expresie="";
                        Stiva<Character> s=new Stiva<>();
                        try {
                            text = area.getText();
                            for(int i=0;i<text.length();i++)
                            {
                                char c=text.charAt(i);
                                if(c>='0' && c<='9')
                                {
                                    expresie+=c;
                                    if(i+1==text.length() || !(text.charAt(i+1)>='0' && text.charAt(i+1)<='9'))
                                    {
                                        expresie+=" ";
                                    }

                                }
                                else if(c=='(')
                                {
                                    s.push(c);
                                }
                                else if(c==')')
                                {
                                    while(!s.isEmpty() && s.top()!='(')
                                    {
                                        expresie+=s.top()+" ";
                                        s.pop();
                                    }
                                    s.pop();
                                }
                                else
                                {
                                    while(!s.isEmpty() && (prioritate(s.top())>=prioritate(c)))
                                    {
                                        expresie+=(s.top()+" ");
                                        s.pop();
                                    }
                                    s.push(c);
                                }

                            }
                            while(!s.isEmpty())
                            {
                                expresie+=(s.top()+" ");
                                s.pop();
                            }
                            System.out.println(expresie);
                            Stiva <Double> st=new Stiva<>();
                            double rezultat=0;
                            String[] op=expresie.split(" ");
                            for(int i=0;i<op.length;i++)
                            {
                                if(op[i].charAt(0)>='0' && op[i].charAt(0)<='9')
                                    st.push(Double.parseDouble(op[i]));
                                else
                                {
                                    double a=st.top();
                                    st.pop();
                                    double b=st.top();
                                    st.pop();
                                    char c=op[i].charAt(0);
                                    switch(c)
                                    {
                                        case '+': rezultat=b+a; break;
                                        case '-': rezultat=b-a; break;
                                        case '*': rezultat=b*a; break;
                                        case '/': rezultat=b/a; break;
                                    }
                                    st.push(rezultat);
                                }
                            }
                            area.append(" = " + rezultat);
                        } catch (Exception e) {
                            area.setText(" !!!Probleme!!! ");
                        }
                    }
                    else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }
    }
}