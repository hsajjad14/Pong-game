/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author Haider
 */
public class Rackets implements Runnable {

    int x, y, ydirection, id;
    Rectangle racket;

    public Rackets(int x, int y, int id) {//method for the rackets passing the y position, y direction, and which player it is.
        this.x = x;
        this.y = y;
        this.id = id;

        racket = new Rectangle(x, y, 15, 150);//creates a rectagle of position x y, widht 15 hieght 150

    }

    public void keyPressed(KeyEvent e) {
        switch (id) {//checks which player number it is
            default:

                break;
            case 1://if player 1
                if (e.getKeyCode() == e.VK_E) {//they control with e and d keys

                    setydirection(-1);
                }
                if (e.getKeyCode() == e.VK_D) {
                    setydirection(1);
                }
                break;
            case 2://if player 2 they control with up and down arrow keys
                if (e.getKeyCode() == e.VK_UP) {
                    setydirection(-1);
                }
                if (e.getKeyCode() == e.VK_DOWN) {
                    setydirection(1);
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {//exact same as keypressed method except it check if the keys have been released
            default:

                break;
            case 1:
                if (e.getKeyCode() == e.VK_E) {

                    setydirection(0);
                }
                if (e.getKeyCode() == e.VK_D) {
                    setydirection(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == e.VK_UP) {
                    setydirection(0);
                }
                if (e.getKeyCode() == e.VK_DOWN) {
                    setydirection(0);
                }
                break;
        }
    }

    public void setydirection(int ydir) {
        ydirection = ydir;//a direction is passed into this method, either +1,-1, or zero.
    }

    public void move() {
        racket.y += ydirection;//adds the direction to the old position to get the new position
        if (racket.y <= 25) {
            racket.y = 25;//checks if rackets arent going above the ceiling
        }
        if (racket.y >= 450){ //checks if they hit the bottom
            racket.y = 450;
        }
    }

    public void draw(Graphics g) {
        switch (id) {//draws each paddle
            default:

                break;
            case 1://draws racket 1
                g.setColor(new Color(110, 61, 23));
                g.fillRect(racket.x, racket.y, racket.width, racket.height);
                break;
            case 2:
                g.setColor(new Color(110, 61, 23));
                g.fillRect(racket.x, racket.y, racket.width, racket.height);
                break;
            //draw racket 2
        }
    }

    @Override
    public void run() {
        try {
            for(;;) {//infinite loop
                move();//move method
                Thread.sleep(3);//sets the thread to sleep
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
