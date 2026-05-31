import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
public class MyClient implements ActionListener {
    JFrame main,parent;
    Font f1,ip_font,chat;
    Color col,parent_col,main_col;
    JPanel p;
    JTextField my_msg, get_ip;
    JLabel l,err,get, msg,ip;
    Socket s;
    JScrollPane sp;
    InetAddress add;
    int i;
    JLabel[] Client_lab = new JLabel[50];
    JLabel[] Server_lab = new JLabel[50];
    DataInputStream din;
    DataOutputStream dout;
    public MyClient() {
        i=0;
        parent_col=new Color(220,255,220);
        main_col=new Color(255,220,220);
        chat=new Font("Series",Font.ITALIC,24);
        f1=new Font("Series",1,18);
        ip_font=new Font("Series",1,20);
        col = new Color(150, 255, 150);
        p = new JPanel();
        main = new JFrame("Client window");
        parent = new JFrame("IP Adress");
        get = new JLabel("Enter your message : ");
        ip = new JLabel();
        err = new JLabel();
        l = new JLabel("Enter the ip of Server : ");
        my_msg = new JTextField(10);
        get_ip = new JTextField(10);
        msg = new JLabel();
        JButton con_btn = new JButton("Connect");
        JButton send_btn = new JButton("Send");
        JButton end_btn = new JButton("Stop");
        sp = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        send_btn.addActionListener(this);
        end_btn.addActionListener(this);
        con_btn.addActionListener(this);
        p.setBackground(col);
        sp.setViewportView(p);
        l.setFont(ip_font);
        get.setFont(f1);
        con_btn.setBackground(Color.GREEN);
        con_btn.setForeground(Color.WHITE);
        con_btn.setBorder(new LineBorder(Color.GREEN, 2, true));
        send_btn.setBackground(Color.BLUE);
        send_btn.setForeground(Color.WHITE);
        send_btn.setBorder(new LineBorder(Color.BLUE, 2, true));
        end_btn.setBackground(Color.RED);
        end_btn.setForeground(Color.WHITE);
        end_btn.setBorder(new LineBorder(Color.RED, 2, true));
        con_btn.setFont(f1);
        end_btn.setFont(f1);
        send_btn.setFont(f1);
        my_msg.setFont(f1);
        l.setFont(f1);
        msg.setFont(f1); 
        err.setFont(ip_font);
        err.setForeground(Color.RED);
        send_btn.setBounds(430, 50, 100, 30);
        end_btn.setBounds(430, 100, 100, 30);
        get.setBounds(50, 50, 200, 30);
        ip.setForeground(Color.BLUE);
        ip.setBounds(550, 50, 2000, 30);
        err.setBounds(50, 150, 3000, 30);
        con_btn.setBounds(500, 45, 150, 40);
        l.setBounds(50, 50, 500, 30);
        my_msg.setBorder(new LineBorder(Color.WHITE,2,true));
        get_ip.setBorder(new LineBorder(Color.WHITE,2,true));
        get_ip.setBounds(280, 45, 200, 40);
        get_ip.setFont(ip_font);
        con_btn.setFont(ip_font);
        my_msg.setBounds(250, 50, 150, 30);
        msg.setBounds(50, 100, 500, 30);
        sp.setBounds(100, 200, 600, 250);
        p.setLayout(new GridLayout(30, 1, 10, 3));
        parent.add(get_ip);
        parent.add(con_btn);
        parent.add(l);
        parent.add(err);
        main.add(send_btn);
        main.add(end_btn);
        main.add(get);
        main.add(my_msg);
        main.add(msg);
        main.add(ip);
        main.add(sp);
        main.setLocationRelativeTo(null);
        main.setLayout(null);        
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.getContentPane().setBackground(main_col);
        parent.getContentPane().setBackground(parent_col);
        parent.setLayout(null);
        parent.setSize(700, 300);
        parent.setLocationRelativeTo(null);
        parent.setResizable(false);
        parent.setVisible(true);
        parent.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().equals("Stop"))
        {
            my_msg.setText("bye");
        }
        if (e.getActionCommand().equals("Connect"))
         {
            try
             {
                s = new Socket(get_ip.getText(), 6666);
                add= s.getInetAddress();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                ip.setText("Connected Server : "+add);
                parent.dispose();
                main.setSize(800, 600);
                main.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
                Thread.sleep(1500);
                main.setVisible(true);
            } 
            catch (Exception ex) 
            {
                get_ip.setText("");
                err.setText("Unable to connect to specified Server !");
                parent.validate();
            }
        }        
        
            if (my_msg.getText().equals(""))
                msg.setText("Please enter a message !");
            else {
                try {
                    String str1 = "", str2 = "";
                    str2 = my_msg.getText();
                    Date d = new Date();
                    dout.writeUTF(str2);
                    my_msg.setText("");
                    dout.flush();
                    str1 = din.readUTF();
                    msg.setText("Server says : " + str1);
                    Server_lab[i] = new JLabel("Server : " + str1 + "   (" + new Date() + ")",SwingConstants.CENTER);
                    Client_lab[i] = new JLabel("Client : " + str2 + "   (" + d + ")",SwingConstants.CENTER);
                    Server_lab[i].setForeground(Color.BLUE);
                    Server_lab[i].setOpaque(true);
                    Server_lab[i].setFont(chat);
                    Server_lab[i].setBackground(Color.WHITE);
                    Server_lab[i].setBorder(new LineBorder(col, 1, true));            
                    Client_lab[i].setOpaque(true);
                    Client_lab[i].setBackground(Color.WHITE);
                    Client_lab[i].setForeground(Color.BLACK);
                    Client_lab[i].setFont(chat);
                    Client_lab[i].setBorder(new LineBorder(col, 1, true));
                    p.add(Client_lab[i]);
                    p.add(Server_lab[i]);
                    i++;
                    if (str1.toLowerCase().equals("bye")&&str2.toLowerCase().equals("bye")) {
                        System.out.println("Session Ended !");
                        Thread.sleep(1500);
                        System.exit(0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        
    }

    public static void main(String args[]) throws Exception {
        new MyClient();        
    }
}







