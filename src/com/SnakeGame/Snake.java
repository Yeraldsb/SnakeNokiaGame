package com.SnakeGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JFrame {

    int widht = 640;
    int height = 480;

    Point comida;
    Point snake;

    ArrayList<Point> lista = new ArrayList<Point>();

    int widhtPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 30;

    ImagenSnake imagenSnake;

    public Snake(){
        setTitle ("Snake");
        setSize(widht, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setLocation(dim.width/2-widht/2, dim.height/2-height/2);  //La ventana emergente queda en el medio de la pantalla.

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                //Para cuando se cierra la ventana salga de la aplicacion

        Teclas teclas = new Teclas();
        this.addKeyListener(teclas);

        startGame();

        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
;    }

    public void startGame(){
        comida = new Point(200,200);
        snake = new Point(widht/2, height/2);

        lista = new ArrayList<Point>();
        lista.add(snake);
        crearComida();
    }

    public void actualizar(){

        if ((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10)) && (snake.y > (comida.y + 10))){
            lista.add(0, new Point(snake.x, snake.y));
            crearComida();
        }

        imagenSnake.repaint();
    }

    public void crearComida(){
        Random rnd = new Random();

        comida.x = rnd.nextInt(widht);
        if ((comida.x % 5) > 0){
            comida.x = comida.x - (comida.x % 5);
        }

        if (comida.x < 5){
            comida.x = comida.x + 10;
        }

        comida.y = rnd.nextInt(height);
        if ((comida.y % 5) > 0){
            comida.y = comida.x - (comida.y % 5);
        }

        if (comida.y < 5){
            comida.y = comida.y + 10;
        }
    }

    public class ImagenSnake extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            g.setColor(new Color(14, 79, 161, 255));
            g.fillRect(snake.x, snake.y, widhtPoint, heightPoint);

            g.setColor(new Color(178, 25, 26, 255));
            g.fillRect(comida.x, comida.y, widhtPoint, heightPoint);
        }
    }

    public class Teclas extends KeyAdapter {                               //KeyAdapter permite evaluar los eventos de las teclas
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }else if (e.getKeyCode() == KeyEvent.VK_UP){
                if (direccion != KeyEvent.VK_DOWN){
                    direccion = KeyEvent.VK_UP;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if (direccion != KeyEvent.VK_UP){
                    direccion = KeyEvent.VK_DOWN;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                if (direccion != KeyEvent.VK_RIGHT){
                    direccion = KeyEvent.VK_LEFT;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                if (direccion != KeyEvent.VK_LEFT){
                    direccion = KeyEvent.VK_RIGHT;
                }
            }
        }
    }

    public  class Momento extends Thread{
        long last =0;
        public void run(){
            while (true){
               if((java.lang.System.currentTimeMillis() - last) > frecuencia){
                   if (direccion == KeyEvent.VK_UP){
                       snake.y = snake.y - heightPoint;
                       if (snake.y > height){
                           snake.y = 0;
                       }
                       if (snake.y < 0){
                           snake.y = height -  heightPoint;
                       }
                   }else if (direccion == KeyEvent.VK_DOWN){
                       snake.y = snake.y + heightPoint;
                       if (snake.y > height){
                           snake.y = 0;
                       }
                       if (snake.y  < 0){
                           snake.y = height - heightPoint;
                       }
                   }else if (direccion == KeyEvent.VK_LEFT){
                       snake.x = snake.x - widhtPoint;
                       if (snake.x > widht){
                           snake.x = 0;
                       }
                       if (snake.x  < 0){
                           snake.x = widht - widhtPoint;
                       }
                   }else if (direccion == KeyEvent.VK_RIGHT){
                       snake.x = snake.x + widhtPoint;
                       if (snake.x > widht){
                           snake.x = 0;
                       }
                       if (snake.x  < 0){
                           snake.x = widht - widhtPoint;
                       }
                   }

                   actualizar();
                    last = java.lang.System.currentTimeMillis();
               }
            }
        }
    }

    public static void main(String[] args) {
	    Snake s = new Snake();
    }
}
