package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener , KeyListener {
    private final int[] snakexlength=new int[750];
    private final int[] snakeylength= new int[750];
     private int lengthofsnake = 3;

     int [] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
     int [] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

     private final Random random = new Random();
     private int enemyx,enemyy;

    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down =false;

    private  boolean gameover=false;

    private  int move=0;
    private int score=0;
   private final ImageIcon Title=new ImageIcon(Objects.requireNonNull(getClass().getResource("snaketitle.jpg")));
    private final ImageIcon leftmouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("leftmouth.png")));
    private final ImageIcon rightmouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("rightmouth.png")));
    private final ImageIcon upmouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("upmouth.png")));
    private final ImageIcon downmouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("downmouth.png")));
    private final ImageIcon snakeimage=new ImageIcon(Objects.requireNonNull(getClass().getResource("snakeimage.png")));
    private final ImageIcon Enemy=new ImageIcon(Objects.requireNonNull(getClass().getResource("enemy.png")));

    private final Timer timer;

    Gamepanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        int delay = 100;
        timer=new Timer(delay,this);
        timer.start();
        newEnemy();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24, 10,851,55);
        g.drawRect(24, 74,851,576);
        Title.paintIcon(this,g,25,11 );
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        if(move==0){
            snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;
            //y
            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;
        }

        if(left){
            leftmouth.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        else if(right){
            rightmouth.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
         else if(up){
            upmouth.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }
        else if(down){
            downmouth.paintIcon(this,g,snakexlength[0], snakeylength[0]);
        }

        for (int i = 1; i< lengthofsnake; i++){
            snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }
        Enemy.paintIcon(this,g,enemyx,enemyy);
        if(gameover){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,50));
            g.drawString("Game Over",300,300);

            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Press Space To Restart",320,350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score :"+score,750,30);
        g.drawString("length :"+lengthofsnake,750,55);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthofsnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if (left) {
            snakexlength[0] = snakexlength[0] - 25;
        }
        if (right) {
            snakexlength[0] = snakexlength[0] + 25;
        }
        if (up) {
            snakeylength[0] = snakeylength[0] - 25;
        }
        if (down) {
            snakeylength[0] = snakeylength[0] + 25;
        }
        if(snakexlength[0]>850) snakexlength [0]=25;
        if(snakexlength[0]<25) snakexlength [0]=850;
        //y position
        if(snakeylength[0]>625) snakeylength [0]=75;
        if(snakeylength[0]<75) snakeylength [0]=625;

        colidewithenemys();
        colidwithbody();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            left=true;
            up=false;
            down=false;
            move++;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            right=true;
            up=false;
            down=false;
            move++;
        }
        else if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left=false;
            right=false;
            up=true;
            move++;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left=false;
            right=false;
            down=true;
            move++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void newEnemy(){
        enemyx=xpos[random.nextInt(34)];//34
        enemyy=ypos[random.nextInt(23)];

        for(int i=lengthofsnake-1;i>=0;i--){
            if(snakexlength[i]==enemyx && snakeylength[i]==enemyy){
                newEnemy();
            }
        }
    }

    private void colidewithenemys(){
        if(snakexlength[0]==enemyx && snakeylength[0]==enemyy){
            newEnemy();
            lengthofsnake++;
            score++;
        }
    }
    private void colidwithbody(){
        for(int i=lengthofsnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
               timer.stop();
               gameover=true;
            }
        }
    }
    private void restart(){
        gameover=false;
        score=0;
        lengthofsnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }
}
