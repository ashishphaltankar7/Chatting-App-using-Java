package chitchat;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.*;

public class Client  implements ActionListener {

    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f=new JFrame();

    public Client() {

        f.setLayout(null);

        // Panel p1 used for Header Section Section of chat
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // Back arrow back Icon seting code
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        //Adding action to back arrow button
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // Dp image Icon seting code
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        // video image Icon seting code
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        // Telephone image Icon seting code
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(340, 20, 35, 30);
        p1.add(phone);

        // 3 dot Image(morevert) image Icon seting code
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel img3dot = new JLabel(i15);
        img3dot.setBounds(390, 20, 15, 25);
        p1.add(img3dot);

        //we can write using JLabel on Frame
        // Writing User name beside progile picture
        JLabel name = new JLabel("User 2");
        name.setBounds(110, 15, 100, 28);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF", Font.BOLD, 20));
        p1.add(name);

        // Writing Active Status below User name 
        JLabel activenow = new JLabel("Active Now");
        activenow.setBounds(110, 35, 100, 28);
        activenow.setForeground(Color.GREEN);
        activenow.setFont(new Font("SAN SERIF", Font.BOLD, 10));
        p1.add(activenow);
        // end of Panel p1 used for Header Section

        //----------------------------------------------------------------------------------------------------------------------
        // a1 Panel used for Chating Section
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // Adding bottom Text field to type a message
        text = new JTextField();
        text.setBounds(5, 575, 310, 40);
        text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(text);

        //Addinf Send Button besdide Text Field
        JButton send = new JButton("Send");
        send.setBounds(320, 575, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
        f.add(send);

        f.setSize(450, 620);
        f.setLocation(800, 10);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        try {
             String out = text.getText();

        JPanel p2 = formatLabel(out);
        //p2.add(output);

        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical, BorderLayout.PAGE_START);

        dout.writeUTF(out);
        
        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\" >" + out + "</p> </html>");

        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        //System.out.println("Hello");
        new Client();

        try {

            Socket s = new Socket("127.0.0.1", 6001);

            DataInputStream din = new DataInputStream(s.getInputStream());

            // to send data
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                a1.setLayout(new BorderLayout());
                
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);

                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                
                // to refreshframe use validate function
                f.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
