import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.sql.*;

class Task
{
    public static void main(String[] args)
    {
        Connection c = null;
        Statement stmt = null;
        String t = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            int x = (int)(Math.random()*((5-1)+1))+1;
            ResultSet rs = stmt.executeQuery("SELECT * FROM text WHERE id = '" + x + "';");
            t = rs.getString("word");
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        JFrame a = new JFrame("Тест скорости печати");
        JLabel Label;
        Label = new JLabel(t);
        Label.setFont(new Font("Verdana", Font.PLAIN, 18));
        Label.setBounds(120,0,1500,70);
        a.add(Label);
        JLabel Label2 = new JLabel();
        Label2.setFont(new Font("Verdana", Font.PLAIN, 28));
        Label2.setBounds(120,150,200,70);
        a.add(Label2);
        JLabel Label3 = new JLabel();
        Label3.setFont(new Font("Verdana", Font.PLAIN, 28));
        Label3.setBounds(120,250,200,70);
        a.add(Label3);
        JTextField textField = new JTextField("");
        textField.setBounds(0,0,0,0);
        a.add(textField);
        JButton b = new JButton("Выход");
        b.setBounds(120,90,85,20);
        a.add(b);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit ( 0 ) ;
            }
        });
        a.setSize(1500,800);
        a.setLayout(null);
        a.setVisible(true);
        String s = Label.getText();
        Label.setText("<html><span style=\"background-color: green; color:white\">"+ Label.getText().charAt(0) +"</span><span style=\"color:black\">"+ Label.getText().substring(1, Label.getText().length()) +"</span></html>");
        textField.addKeyListener(new KeyAdapter()
        {
            int i = 0, g = 1;
            double k = 100;
            long time;
            public void keyPressed(KeyEvent e)
            {
                if (i + 1 == s.length() && s.charAt(i) == e.getKeyChar())
                {
                    Label2.setText("<html>Скорость"+ "<br/>" + Long.toString(s.length()*60000/(System.currentTimeMillis() - time)) + " зн./мин</html>");
                    Label3.setText("<html>Точность"+ "<br/>" + String.format("%.2f",k) + " %</html>");
                    Label.setText(s);
                    Label.setText("<html><span style=\"background-color: green; color:white\">"+ Label.getText().charAt(0) +"</span><span style=\"color:black\">"+ Label.getText().substring(1, Label.getText().length()) +"</span></html>");
                    i = 0;
                    k = 100;
                }
                else if (!(e.isShiftDown()&&s.charAt(i) != e.getKeyChar()))
                {
                    if (s.charAt(i) == e.getKeyChar())
                    {
                        if (i == 0)
                        {
                            time = System.currentTimeMillis();
                            Label2.setText("");
                            Label3.setText("");
                        }
                        Label.setText(s);
                        Label.setText("<html><span style=\"color: green\">"+Label.getText().substring(0, i + 1)+"</span><span style=\"background-color: green; color:white\">"+ Label.getText().charAt(i + 1) +"</span><span style=\"color:black\">"+ Label.getText().substring(i + 2, Label.getText().length()) +"</span></html>");
                        i++;
                        g = 1;
                    }
                    else
                    {
                        Label.setText(s);
                        Label.setText("<html><span style=\"color: green\">"+Label.getText().substring(0, i)+"</span><span style=\"background-color: red; color:white\">"+ Label.getText().charAt(i) +"</span><span style=\"color:black\">"+ Label.getText().substring(i + 1, Label.getText().length()) +"</span></html>");
                        if (g == 1)
                        {
                            k = k - (double)100/s.length();
                            g = g + 1;
                        }
                    }
                }
            }
        });
    }
}
