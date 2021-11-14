package com.company;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Dimension;

public class Snake extends JFrame {

    int widht = 640;
    int height = 480;

    public Snake(){
        setTitle ("Snake");
        setSize(widht, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setVisible(true);
    }

    public static void main(String[] args) {
	    Snake s = new Snake();
    }
}
