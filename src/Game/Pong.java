/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

/**
 *Haider Sajjad and Rehan Gill
 * Pong game, plays pong (tennis)
 * June 19, 2017
 */
public class Pong extends JFrame {

    int width = 800, height = 600;//dimensions of the main backdrop of the game
    Dimension dimension = new Dimension(width, height);
    Image dbImage; //using double buffering rather than directly rendering to elimiinate
    Graphics dbg;


    Rectangle startB = new Rectangle(300, 180, 300, 30);//creating the buttons
    Rectangle InfoButton = new Rectangle(300, 230, 300, 30);
    Rectangle exitB = new Rectangle(300, 280, 300, 30);
    Rectangle backB = new Rectangle(300, 420, 300, 30);

    Rectangle restartB = new Rectangle(300, 330, 300, 30);

    static Ball b = new Ball(390, 290); //creating an object to hold the ball class.

    Thread ball = new Thread(b);//creating threads, ball  thread
    Thread p1 = new Thread(b.p1);//player 1 thread
    Thread p2 = new Thread(b.p2);//player 2 thread

    boolean started = false;//boolean variables to control the methods of the game
    boolean over = false;
    boolean difficultyclicked = false;
    boolean infoclicked = false;

    int scorewon = 7;//variable for winning score
    String winner;//variable to hold the winner
    boolean wongame = false;

    public Pong() {
        //method instating the class

        this.setSize(dimension);//adding the dimensions
        this.setResizable(false);
        this.setTitle("Pong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(178, 223, 224));//background colour
        this.setVisible(true);///setting visible
        this.addKeyListener(new Al());//adding action listener
        this.addMouseListener(new MouseHandler());//adding mouselistener
        this.addMouseMotionListener(new MouseHandler());
    }

    public class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            int mousex = e.getX();//gets the mouse's x position
            int mousey = e.getY();//gets the mouse's y position

            if (mousex > startB.x && mousex < startB.x + startB.width && mousey > startB.y && mousey < startB.y + startB.height) {//checks if the mouse is hovering over the start button when clicked
                start();//calls the start method to start the threads
            }
            if (mousex > exitB.x && mousex < exitB.x + exitB.width && mousey > exitB.y && mousey < exitB.y + exitB.height) {
                System.exit(0);//closes the program if this button is clicked
            }
            if (mousex > restartB.x && mousex < restartB.x + restartB.width && mousey > restartB.y && mousey < restartB.y + restartB.height) {

                b.p1score = 0;//sets the scores to zero
                b.p2score = 0;

                started = true;//brings the game panel to the front
                infoclicked = false;//hides the other 2 panels
                over = false;

            }
            if (mousex > InfoButton.x && mousex < InfoButton.x + InfoButton.width && mousey > InfoButton.y && mousey < InfoButton.y + InfoButton.height) {
                infoclicked = true;//brings the description to the front if clicked
            }
            if (mousex > backB.x && mousex < backB.x + backB.width && mousey > backB.y && mousey < backB.y + backB.height) {
                infoclicked = false;//goes back to main menue page if clicked
            }

        }
    }

    public void start() {
        started = true;//variable to control what page is at the front for viewing
        ball.start();//starts the threads
        p1.start();
        p2.start();
    }

    public static void main(String[] args) {
        Pong p = new Pong();//starts running the program
        //declares the threads
        Thread ball = new Thread(b);
        Thread p1 = new Thread(b.p1);
        Thread p2 = new Thread(b.p2);

    }

    @Override
    public void paint(Graphics g) {
        //painting the program
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);

    }

    public void paintComponent(Graphics g) {
        if (!started && !over) {//if started button is not clicked and game is not over, main menue appears

            g.setFont(new Font("Arial", Font.BOLD, 50));    //painting the title and all the buttons on the main menue
            g.setColor(Color.BLACK);
            g.drawString("Pong", 300, 120);

            g.setColor(Color.DARK_GRAY);
            g.fillRect(startB.x, startB.y, startB.width, startB.height);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Start Game", startB.x + 20, startB.y + 25);

            g.setColor(Color.DARK_GRAY);
            g.fillRect(exitB.x, exitB.y, exitB.width, exitB.height);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Click to close ", exitB.x + 20, exitB.y + 25);

            g.setColor(Color.DARK_GRAY);
            g.fillRect(InfoButton.x, InfoButton.y, InfoButton.width, InfoButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Click to learn to play ", InfoButton.x + 20, InfoButton.y + 25);

            if (infoclicked) {//if the information button is clicked on the main menue, instructions screen appears with the back button which sets infoclicked to false to go back to the menue page
                g.setColor(new Color(178, 223, 224));
                g.fillRect(0, 0, this.width, this.height);

                g.setFont(new Font("Arial", Font.BOLD, 20));//painting the instructions
                g.setColor(Color.BLACK);
                g.drawString("Game Description:", 75, 200);
                g.drawString("Two players are required to play this game. ", 75, 240);
                g.drawString("The objective of the game is to stop the “ball” from", 75, 260);
                g.drawString("hitting your side of the wall and bounce it back to the opponent's side.", 75, 280);
                g.drawString("This is done by moving the slider you have using the", 75, 300);
                g.drawString("up and down arrows for player 2 and “E” and “D” for player 1.", 75, 320);
                g.drawString("Every time a player fails to deflect the ball back,", 75, 340);
                g.drawString("the opposing player scores a point.", 75, 360);
                g.drawString("The first player to reach score of 7 wins!", 75, 400);

                g.setColor(Color.DARK_GRAY);//painting back  button
                g.fillRect(backB.x, backB.y, backB.width, backB.height);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.setColor(Color.WHITE);
                g.drawString("Click to go back ", backB.x + 20, backB.y + 25);

            }

        } else if (over && !started) {//game over page, appears if score reaches 7, and game is not running
            //paints the end game screen, congratulating the winner and asking if they want to play again.
            g.setColor(new Color(178, 223, 224));
            g.fillRect(0, 0, this.width, this.height);

            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setColor(Color.BLACK);
            g.drawString("Game Over", 100, 150);

            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setColor(Color.BLACK);
            g.drawString(winner + " won!", 100, 200);

            g.setColor(Color.DARK_GRAY);//button to close game
            g.fillRect(exitB.x, exitB.y, exitB.width, exitB.height);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Click to close ", exitB.x + 20, exitB.y + 25);

            g.setColor(Color.DARK_GRAY);//button to restart
            g.fillRect(restartB.x, restartB.y, restartB.width, restartB.height);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Click to play again ", restartB.x + 20, restartB.y + 25);

        } else {//if neither main menue or game over screen are painted this means the game is running
            b.draw(g);//drawing the ball class
            b.p1.draw(g);//drawing racket 1
            b.p2.draw(g);//drawing racket 2

            g.setColor(Color.BLACK);//painting the scores
            g.drawString("player 1 score: " + b.p1score, 50, 50);
            g.drawString("player 2 score:" + b.p2score, 700, 50);
            if (b.p1score == scorewon) {//checking if there is a winner
                started = false;
                over = true;
                winner = "Player 1";

            } else if (b.p2score == scorewon) {
                started = false;
                over = true;
                winner = "Player 2";

            }

        }
        repaint();//repainting
    }

    //event listener
    public class Al extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            b.p1.keyPressed(e);//gets the keys pressed, passing them into the racket class
            b.p2.keyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            b.p1.keyReleased(e);//gets the keys released, passing them into the racket class
            b.p2.keyReleased(e);

        }
    }

}

