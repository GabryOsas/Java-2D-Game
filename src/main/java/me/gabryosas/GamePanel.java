package me.gabryosas;

import me.gabryosas.entity.Player;
import me.gabryosas.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable{
    // Screen settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
   public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //serve per rendere il nostro gioco vivo, ex 60 FPS = la finestra viene aggiornata 60 volte in un secondo
    Player player = new Player(this, keyH);

    //parametri base per il giocatore
    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this); //creiamo il nostro Thread per questa classe
        gameThread.start(); //avviamo il thread
    }

    @Override //aumaticamente se noi creiamo un thread, viene eseguito questo metodo
    public void run() {
            /*se il gameThread esiste allora ripetiamo il codice dentro, sarà il nostro loop
            funzioni:
            1 UPDATE: update information such as character positions

             2 DRAW: draw the screen with the updated information*/

        double drawInterval = 1000000000 / FPS; //ogni 0.016 secondi
        double nextDrawTime = System.nanoTime() + drawInterval;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            update();

            repaint(); //così siamo in grade di chiamare il paintComponent
            drawCount++;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; //la funzione sleep accetta solo millisecondi

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); //non farà niente finchè non è finito

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (timer > 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }
    }
    public void update(){
        player.update();
        /*Y se aumanta va sotto perché la coordinata di base si trova a (0; 0)
        X aumenta va a destra*/
    }
    //g è il nostro paint tool
    public void paintComponent(Graphics g) { //metodo predefinito in Java

        super.paintComponent(g);
        /*è un'estensione della classe Graphics che ci consente di avere
        un controllo sulla geometria, coordinate, colori e testo*/
        Graphics2D g2 = (Graphics2D) g; //ha più funzioni

        tileManager.draw(g2); //prima così creiamo dei layer ;P
        player.draw(g2);

        g2.dispose(); //funziona anche senza questo metodo, ma così salviamo della memoria, perché rilascia le risorse che stiamo usando

    }
}
