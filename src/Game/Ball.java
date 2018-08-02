/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Haider
 */
public class Ball implements Runnable {

    int x, y, xdirection, ydirection;//positions of the ball, as well as its dimensions
    Rectangle ball = new Rectangle();//making the ball a square


    //score
    int p1score, p2score;
    int hard =5;

    Rackets p1 = new Rackets(40, 250, 1);//calling the rackets from racket class, passing the neccessary information
    Rackets p2 = new Rackets(750, 250, 2);



    public Ball(int x, int y) {
        p1score = 0;//setting the scores to 0 at the begining
        p2score = 0;

        this.x = x;//getting the x and y posiiton of the ball
        this.y = y;
        //sets random direction for the ball
        Random r = new Random();
        if (r.nextBoolean()) {//ball randomly goes either forward or backwards
            setxdirection(-1);//passes -1 in the x direction method
        } else {
            setxdirection(1);
        }
        if (r.nextBoolean()) {//ball randomly goes either up or down
            setydirection(-1);
        } else {
            setydirection(1);
        }
        //create ball,
        ball = new Rectangle(this.x, this.y, 20, 20);

    }

    public void draw(Graphics g) {
        //painting the ball
        g.setColor(new Color(153, 93, 169));
        g.fillRect(ball.x, ball.y, ball.width, ball.height);


    }

    public void collisionDetection() {//checking if ball colides with rackets, to revers its direction.
        if (ball.intersects(p1.racket)) {
            setxdirection(1);//sets direction forward if it hits player 1 racket(on the left)
        }
        if (ball.intersects(p2.racket)) {
            setxdirection(-1);//sets direction backward if it hits player 2 racket(on the right)
        }

    }

    public void move() {
        collisionDetection();//calls the collision method
        ball.x += xdirection;//adds the new direction with current position to change its position, making ball move
        ball.y += ydirection;
        if (ball.x <= 0) { //checking if ball hits the left wall
            setxdirection(1);//reverses direction
            p2score++;//adds to player 2's score

        }
        if (ball.x >= 770) {//hits right wall
            setxdirection(-1);
            p1score++;


        }
        if (ball.y <= 15) {//hits ceiling
            setydirection(1);

        }
        if (ball.y >= 570) {//hits the ground
            setydirection(-1);

        }

    }

    public void setxdirection(int xdir) {
        xdirection = xdir; //sets the xdirection to what was passed in either when the ball hit a wall, or at the begining when it is picked at random
    }

    public void setydirection(int ydir) {
        ydirection = ydir;
    }

    @Override
    public void run() {//run method
        try {
            for (;;) {//infinite loop
                move();//calls the move method
                Thread.sleep(2);//sleeps every 2 miliseconds
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

