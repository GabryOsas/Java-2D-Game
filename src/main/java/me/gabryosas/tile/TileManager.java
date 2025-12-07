package me.gabryosas.tile;

import me.gabryosas.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tile.length; i++){
                tile[i] = new Tile();
            }

            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){ //metodo per caricare la mappa
        try {
            InputStream is = getClass().getResourceAsStream("/maps/default-map.txt"); //leggiamo il file che contiene i nostri tile
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol &&
            row < gp.maxScreenRow) {

                //prendiamo ogni linea
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" "); //qui facciamo un split prendendo ogni singolo dato ex 1, 2 , 0

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num; //aggiungiamo i nostri tile (numerici) alla matrice
                    col++;
                }
                if (col == gp.maxScreenCol){ //in questo modo scendiamo di righe appena abbiamo finito di analizzare la colonna
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e){

        }
    }

    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol &&
        row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);

            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
