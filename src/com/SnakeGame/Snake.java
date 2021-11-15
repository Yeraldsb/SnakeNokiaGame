package com.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JFrame {

    int widht = 700;
    int height = 550;

    Point comida;
    Point snake;

    boolean gameOver = false;

    ArrayList<Point> lista = new ArrayList<Point>();

    int longitud = 2;

    int widhtPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 30;

    ImagenSnake imagenSnake;

    public Snake() {
        setTitle("Snake");
        startGame();
        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);
        setSize(widht, height);

        this.addKeyListener(new Teclas());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void startGame() {
        comida = new Point(200, 100);
        snake = new Point(widht/2, height/2);

        lista = new ArrayList<Point>();
        lista.add(snake);
        longitud = lista.size();

        crearComida();
    }

    public void actualizar() {

        lista.add(0, new Point(snake.x, snake.y));
        lista.remove((lista.size() - 1));

        for (int i = 1; i < lista.size(); i++) {
            Point punto = lista.get(i);
            if (snake.x == punto.x && snake.y == punto.y) {
                gameOver = true;
            }

        }
        if ((snake.x > (comida.x-10) && snake.x < (comida.x+10)) && (snake.y > (comida.y-10) && snake.y < (comida.y+10))) {
            lista.add(0, new Point(snake.x, snake.y));
            crearComida();
        }

        imagenSnake.repaint();
    }

    public void crearComida() {
        Random rnd = new Random();

        comida.x = (rnd.nextInt(widht)) + 5;
        if((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);
        }

        if(comida.x < 5) {
            comida.x = comida.x + 10;
        }
        if(comida.x > widht) {
            comida.x = comida.x - 10;
        }

        comida.y = (rnd.nextInt(height)) + 5;
        if((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);
        }

        if(comida.y > height) {
            comida.y = comida.y - 10;
        }
        if(comida.y < 0) {
            comida.y = comida.y + 10;
        }

    }

    public class ImagenSnake extends JPanel {
        public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(gameOver) {
                    g.setColor(new Color(0,0,0));
                } else {
                    g.setColor(new Color(255,255,255));
                }
                g.fillRect(0,0, widht, height);
                g.setColor(new Color(0,0,255));

                if(lista.size() > 0) {
                    for(int i=0;i<lista.size();i++) {
                        Point p = (Point)lista.get(i);
                        g.fillRect(p.x,p.y,widhtPoint,heightPoint);
                    }
                }

                g.setColor(new Color(255,0,0));
                g.fillRect(comida.x,comida.y,widhtPoint,heightPoint);

                if(gameOver) {
                    g.setFont(new Font("TimesRoman", Font.BOLD, 40));
                    g.drawString("GAME OVER", 300, 200);
                    g.drawString("SCORE "+(lista.size()-1), 300, 240);

                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g.drawString("N to Start New Game", 100, 320);
                    g.drawString("ESC to Exit", 100, 340);
                }

            }
    }

    public class Teclas extends KeyAdapter {
        @Override                               //KeyAdapter permite evaluar los eventos de las teclas
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
            }
        }
    }

    public class Momento extends Thread {
        long last = 0;

        public Momento(){
        }

        public void run() {

            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {
                    if (!gameOver) {
                        if (direccion == KeyEvent.VK_UP) {
                            snake.y = snake.y - heightPoint;
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                        } else if (direccion == KeyEvent.VK_DOWN) {
                            snake.y = snake.y + heightPoint;
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                        } else if (direccion == KeyEvent.VK_LEFT) {
                            snake.x = snake.x - widhtPoint;
                            if (snake.x > widht) {
                                snake.x = 0;
                            }
                            if (snake.x < 0) {
                                snake.x = widht - widhtPoint;
                            }
                        } else if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x = snake.x + widhtPoint;
                            if (snake.x > widht) {
                                snake.x = 0;
                            }
                            if (snake.x < 0) {
                                snake.x = widht - widhtPoint;
                            }
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
