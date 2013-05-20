package com.mpx.birjan.client.editor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SelectFile {

    public static void main(String[]args) {

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Select File for Linking");
                // don't use null layouts.
                //frame.setLayout(null);

                // create a panel so we can add a border
                JPanel container = new JPanel(new FlowLayout(3));
                container.setBorder(new EmptyBorder(10,10,10,10));
                frame.setContentPane(container);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // instead call pack() after components are added
                //frame.setSize(400, 100);

                final JTextField text=new JTextField(20);

                JButton b=new JButton("Select File");

                // irrelevant unless button stretched by layout
                //b.setHorizontalAlignment(SwingConstants.LEFT);
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jfc = new JFileChooser(System.getProperty("user.home"));
                        jfc.setSelectedFile(new File("data.xls"));
                        jfc.showSaveDialog(null);
                        int returnVal = jfc.showSaveDialog(null);  
                        if (returnVal == JFileChooser.APPROVE_OPTION)
                        {
//                            File file = jfc.getSelectedFile();
                            // save the file.
                            BufferedWriter bw;
                            try {
                                bw = new BufferedWriter(new FileWriter(jfc.getSelectedFile()));
                                bw.write("A");
                                bw.flush();
                            }               
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                            }

                        }
                        else
                        {
                            System.out.println("Save command cancelled by user. ");
                        }
//                        JFileChooser fc = new JFileChooser();
//                        String desc = "Excel ( *.xls)";
//                        String[] types = {".xls"};
//                        fc.addChoosableFileFilter(
//                            new FileNameExtensionFilter(desc, types));

//                        int returnval = fc.showOpenDialog(null);
//                        if (returnVal == JFileChooser.APPROVE_OPTION) {
//                            File file = fc.getSelectedFile();
//                            text.setText(file.getPath());
//                            try {
//                                // 1.6+
//                                Desktop.getDesktop().edit(file);
//                            } catch(Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        }
                    }
                });
                container.add(text);
                container.add(b);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
