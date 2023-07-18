
//to run the GUI fill the first bomb with the size of matrix  ex.3*3  and seconed box with bomb place (put space between the first and second bomb place) ex. 1,3 2,1 ...... to try another seacrh type please press reset first to clean the old screen

import org.jpl7.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PROGUI extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField textField;
    private JTextField textField2;
    private JButton button;
    private JButton button2;
    JPanel buttonPanel;
    JRadioButton r;
    JRadioButton r2;
    static int CO = 0;

    public PROGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Assignment 2");
        setSize(700, 700);
        setResizable(false);
        panel = new JPanel(new GridLayout(2, 5, 10, 10));
        textField = new JTextField();
        r2 = new JRadioButton("Informed");
        r = new JRadioButton("Uninformed");
        JLabel l = new JLabel("Borad dimentions");
        JLabel l2 = new JLabel("Bomb places");
        textField2 = new JTextField(1);
        ButtonGroup group = new ButtonGroup();
        button2 = new JButton("Reset");
        button2.addActionListener(this);
        group.add(r);
        group.add(r2);
        textField.setSize(100, 30);
        textField2.setSize(100, 30);
        button = new JButton("Start");
        button.addActionListener(this);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.add(l);
        textPanel.add(textField);
        textPanel.add(l2);
        textPanel.add(textField2);
        textPanel.add(r);
        textPanel.add(r2);
        textPanel.add(button2);
        add(panel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PROGUI();
    }

    String Start = "";
    String END = "";

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button2) {
            button.setText("Start");
            CO = 0;
            panel.remove(buttonPanel);
            getContentPane().remove(buttonPanel);
            repaint();
        }
        if (e.getSource() == button && r.isSelected()) {
            Prolog("'uninformed.pl'");
        } else if (e.getSource() == button && r2.isSelected()) {
            Prolog("'informed.pl'");
        } else if (e.getSource() == button && r2.isSelected() == false && r.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Please select the type of search");
        }
    }

    void Prolog(String Malaf) {
        Query q = new Query("consult(" + Malaf + ")");
        q.hasSolution();
        String QUERYYY = "bsearch(";
        Start = textField.getText();
        END = textField2.getText();
        QUERYYY += Start.charAt(0) + "," + Start.charAt(2) + "," + END.charAt(0) + "," + END.charAt(2) + ","
                + END.charAt(4) + "," + END.charAt(6) + ",L).";
        String IQ = QUERYYY;
        q = new Query(IQ);
        Map<String, Term>[] res = q.allSolutions();
        if (CO == res.length) {
            dispose();
        }
        button.setText(CO + 1 + "/" + res.length);
        String[] arr = res[CO].toString().replaceAll("[{}L=\\s]", "").replaceAll("\\[", "").replaceAll("\\]", "")
                .split(",");
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Integer.parseInt(arr[i]);
        }
        int[] arqam = result;
        /*-----------------------------------------------------------------------------------------------------------*/
        if (CO > 0) {
            panel.remove(buttonPanel);
            getContentPane().remove(buttonPanel);
        }

        buttonPanel = new JPanel(new GridLayout(Integer.parseInt(Character.toString(IQ.charAt(8))),
                Integer.parseInt(Character.toString(IQ.charAt(10)))));
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
        int all = Integer.parseInt(Character.toString(IQ.charAt(8)))
                * Integer.parseInt(Character.toString(IQ.charAt(10)));
        try {
            Image Bomb = ImageIO.read(new File("BOMB.png"));
            Image Up = ImageIO.read(new File("Half Up.jpg"));
            Image Down = ImageIO.read(new File("Half Down.jpg"));
            Image Left = ImageIO.read(new File("Half Left.jpg"));
            Image Right = ImageIO.read(new File("Half right.jpg"));
            Image Plain = ImageIO.read(new File("Plain.jpg"));
            JButton imgButton;
            for (int i = 0; i < all; i++) {
                if (arqam[i] == 2) {
                    imgButton = new JButton(new ImageIcon(Up.getScaledInstance(100, 115, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                    arqam[i + Integer.parseInt(Character.toString(IQ.charAt(10)))] = -1;
                } else if (arqam[i] == -1) {
                    imgButton = new JButton(new ImageIcon(Down.getScaledInstance(100, 115, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                } else if (arqam[i] == 1) {
                    imgButton = new JButton(new ImageIcon(Left.getScaledInstance(115, 100, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                    arqam[i + 1] = -4;
                } else if (arqam[i] == -4) {
                    imgButton = new JButton(new ImageIcon(Right.getScaledInstance(115, 110, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                } else if (arqam[i] == 3) {
                    imgButton = new JButton(new ImageIcon(Bomb.getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                } else {
                    imgButton = new JButton(new ImageIcon(Plain.getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
                    imgButton.setBackground(Color.WHITE);
                }
                buttonPanel.add(imgButton);
                imgButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                    }
                });
            }
        } catch (IOException ex) {
            System.out.println("Error loading image");
        }
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
        CO++;
    }
}
