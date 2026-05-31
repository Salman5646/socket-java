import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
public class MyServer implements ActionListener{
    JFrame main,parent;
    Color main_col,parent_col;
    JTextField my_msg,client_msg;
    JLabel wait,msg,get,connected,empty;
    ServerSocket ss;
    Socket s;
    Font f,f1;
    DataInputStream din;
    DataOutputStream dout;
    public MyServer() 
    {       
        parent_col=new Color(200,200,255);
        main_col=new Color(200,255,200);
        f1=new Font("Series",1,18);
        f=new Font("Series",1,24);
        parent= new JFrame("Connection Window");
        main= new JFrame("Server Window");
        wait=new JLabel("Waiting For Client...");
        empty=new JLabel();
        connected=new JLabel();
        get=new JLabel("Enter your message : ");
        my_msg=new JTextField(10);
        client_msg=new JTextField(10);
        msg=new JLabel("Client says : ");
        JButton send_btn=new JButton("Send");
        JButton end_btn = new JButton("Stop");
        send_btn.addActionListener(this);
        end_btn.addActionListener(this);
        end_btn.setBounds(420, 100, 100, 30);
        send_btn.setBounds(420,50,100,30);
        get.setBounds(50, 50, 200, 30);
        wait.setBounds(150, 150, 500, 30);
        wait.setFont(f);
        my_msg.setBounds(250, 50, 150, 30);
        client_msg.setBounds(250, 100, 150, 30);
        my_msg.setBorder(new LineBorder(Color.WHITE,2,true));
        client_msg.setBorder(new LineBorder(Color.WHITE,2,true));
        client_msg.setEditable(false);
        msg.setBounds(50, 100, 500, 30);
        empty.setForeground(Color.RED);
        empty.setBounds(50, 150, 500, 30);
        connected.setBounds(550, 50, 200, 30);
        send_btn.setBackground(Color.BLUE);
        send_btn.setForeground(Color.WHITE);
        send_btn.setBorder(new LineBorder(Color.BLUE, 2, true));
        end_btn.setBackground(Color.RED);
        end_btn.setForeground(Color.WHITE);
        end_btn.setBorder(new LineBorder(Color.RED, 2, true));
        end_btn.setFont(f1);
        send_btn.setFont(f1);
        client_msg.setFont(f1);
        my_msg.setFont(f1);
        msg.setFont(f1);
        get.setFont(f1);
        main.add(send_btn);
        main.add(end_btn);
        main.add(get);
        main.add(my_msg);
        main.add(client_msg);
        main.add(msg);
        main.getContentPane().setBackground(main_col);
        main.setLayout(null);
        main.setVisible(false);
        main.setSize(800,250);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
        parent.add(wait);
        parent.getContentPane().setBackground(parent_col);
        parent.setLayout(null);
        parent.setSize(600, 400);
        parent.setResizable(false);
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
        parent.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
        try{
            ss=new ServerSocket(6666);
            s=ss.accept();
            InetAddress add=ss.getInetAddress();
            wait.setForeground(Color.WHITE);
            wait.setText("Connected Client : "+add);
            Thread.sleep(1500);
            parent.dispose();
            main.setVisible(true);
            connected.setForeground(Color.BLUE);
            connected.setText("Connected Client : "+add);
            main.add(connected);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e) 
    {       
        try{
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();;
        }
        if (e.getActionCommand().equals("Stop"))
        {
            my_msg.setText("bye");
        }
        if(my_msg.getText().equals(""))
        empty.setText("Please enter a message !");
        else{ 
            try{ 
                String str1="",str2="";
                str1=din.readUTF();
                client_msg.setText(""+str1);
                str2=my_msg.getText();
                my_msg.setText("");
                dout.writeUTF(str2);
                dout.flush();            
                if(str1.toLowerCase().equals("bye")&&str2.toLowerCase().equals("bye"))
            {            
                ss.close();
                System.out.println("Session Ended !");
                Thread.sleep(1500);
                System.exit(0);          
        }
        
    }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
    public static void main(String args[]) throws Exception
    {    
     new MyServer();   
    }
}
