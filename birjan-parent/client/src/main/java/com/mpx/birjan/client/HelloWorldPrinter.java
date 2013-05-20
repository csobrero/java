package com.mpx.birjan.client;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
 
public class HelloWorldPrinter implements Printable, ActionListener {
 
 
    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {
 
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
//        Paper paper = pf.getPaper();
//        paper.setSize(1297, 2420);
//        pf.setPaper(paper);
        System.out.println(pf.getHeight());
        System.out.println(pf.getWidth());
        System.out.println(pf.getImageableHeight());
        System.out.println(pf.getImageableWidth());
        System.out.println(pf.getImageableX());
        System.out.println(pf.getImageableY());
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX()*2, pf.getImageableY()*2);
 
        Font fnt = new Font("Helvetica", Font.PLAIN, 10);
        g.setFont(fnt);
        /* Now we perform our rendering */
        g.drawString("29/03/2012 14:33", 20, 20);
//        g.drawString("QWERTY1234", 0, 10);
 
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
 
    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
 
    public static void main(String args[]) {
  
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Hello World Printer");
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Print Hello World");
        printButton.addActionListener(new HelloWorldPrinter());
        f.add("Center", printButton);
        f.pack();
        f.setVisible(true);
    }
}
