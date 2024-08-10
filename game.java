import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;


public class game extends JFrame implements KeyListener {
    snake s = new snake();
    box[][] map = new box[50][50];
    Timer timer= new Timer();
    boolean newgame =false;
    //Game Logic
    TimerTask task = new TimerTask(){
        @Override
        public void run(){
            int x=s.headPosition.x/10;
            int y=s.headPosition.y/10;
            switch (s.dir) {
                case UP:if (y-1>2){s.headPosition=map[x][y-1];}else{gameover();};
                    break;
                case DOWN:if (y+1<50){s.headPosition=map[x][y+1];}else{gameover();};
                    break;
                case LEFT:if (x-1>0){s.headPosition=map[x-1][y];}else{gameover();};
                    break;
                case RIGHT:if (x+1<50){s.headPosition=map[x+1][y];}else{gameover();};
                    break;
            }
            if(s.headPosition.filled){
             timer.cancel();
             gameover();
             System.out.println("game over");
            }else{
                score++;
                s.headPosition.snakePassed();
                repaint();
            }
        }
    };
    

    void gameover(){
        if (!newgame){
            new game();
            this.newgame=true;
            this.dispose();
        }
        
    }
    int score=0;
    final int X = 0, Y = 0, WIDTH = 500, HEIGHT = 500,SPEED=6;
    game() {
        // setting frame
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setVisible(true);
        this.setTitle("Snake");
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);

        // setting map
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                map[i][j] = new box(i * 10, j * 10);
            }
        }
        //Timeout timer
        timer.scheduleAtFixedRate(task, 0, 1000/SPEED);

    }

    // Rendering code
    @Override
    public void paint(Graphics g) {
        Graphics2D c = (Graphics2D) g;
        c.setColor(Color.BLACK);
        c.fillRect(X, Y, WIDTH, HEIGHT);
        renderer(c);
    }
    //Input
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP){
            s.dir=Direction.UP;
        } else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            s.dir=Direction.DOWN;
        } else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            s.dir=Direction.RIGHT;
        } else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            s.dir=Direction.LEFT;
        }
    }

    // overrides for interfaces
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    public enum Direction {
        UP, DOWN, RIGHT, LEFT
    }
    // snake
    public class snake { 
        public Direction dir = Direction.RIGHT;
        box headPosition = new box(50, 50);
    }

    // box
    public class box {
        boolean filled = false;
        int x;
        int y;
        int width = 10;
        int height = 10;

        box(int x, int y) {
            this.x = x;
            this.y = y;
        }
        box(int x, int y,boolean passed) {
            this.x = x;
            this.y = y;
            this.filled=passed;
        }
        void snakePassed() {
            this.filled = true;
        }

        void draw(Graphics2D c) {
         
            if (filled) {
                c.setColor(Color.GREEN);
                c.fillRect(this.x, this.y, this.width, this.height);
            } 
        }
    }

    void renderer(Graphics2D canvas) {
     this.setTitle("SCORE : " + Integer.toString(score));   
     for (box[] boxes : map) {
        for (box box : boxes) {
            box.draw(canvas);
        }
     }
    }
}
