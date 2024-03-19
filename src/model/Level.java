package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
    private Entity[][] board;
    private ArrayList<Player> players;
    public Level(int levelNumber) throws IOException {
        players = new ArrayList<>();
        try {
            BufferedReader  reader = new BufferedReader(new FileReader("src/assets/levels/level" + levelNumber + ".txt"));

            String currentLine;

            board = new Entity[15][15];
            int rowIndex = 0;
            while((currentLine = reader.readLine()) != null) {
                for(int colIndex = 0; colIndex < 15; colIndex++) {
                    char currentObject = currentLine.charAt(colIndex);

                    board[rowIndex][colIndex] = generateEntity(currentObject, rowIndex, colIndex);

                    if(currentObject == 'p') {
                            players.add(new Player(rowIndex, colIndex));
                    }
                }

                rowIndex++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private Entity generateEntity(char entity, int rowIndex, int colIndex) {
        return switch (entity) {
            case 'w' -> new Wall(rowIndex, colIndex);
            case 'c' -> new Chest(rowIndex, colIndex);
            case 'p' -> new Floor(rowIndex, colIndex);
            case 'f' -> new Floor(rowIndex, colIndex);
            case 'm' -> new Monster(rowIndex, colIndex);
            default -> new Entity();
        };
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j].draw(g2);
            }
        }
    }
}
