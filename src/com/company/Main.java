package com.company;


import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

public class Main {
    public Main() {
    }

    public static void main(String[] var0) {
        JFrame var1 = new JFrame("Java spil");
        var1.setVisible(true);
        var1.setDefaultCloseOperation(3);
        var1.setResizable(false);
        int[] var2 = new int[256];
        Image var3 = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, var2, 0, 16));
        Cursor var4 = Toolkit.getDefaultToolkit().createCustomCursor(var3, new Point(0, 0), "invisiblecursor");
        var1.setCursor(var4);
        var1.getContentPane().setLayout(new BorderLayout());
        var1.setAlwaysOnTop(true);
        new Controller(var1);
    }
}

