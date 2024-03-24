package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Level {
    private Entity[][] board;
    private ArrayList<Player> players;
    private ArrayList<Floor> floorTiles;
    private ArrayList<Wall> wallTiles;

    public Level(int levelNumber) throws IOException {
        players = new ArrayList<>();
        try {
            BufferedReader  reader = new BufferedReader(new FileReader("src/assets/levels/level" + levelNumber + ".txt"));

            String currentLine;

            board = new Entity[15][15];
            floorTiles = new ArrayList<>();
            wallTiles = new ArrayList<>();
            int rowIndex = 0;
            while((currentLine = reader.readLine()) != null) {
                for(int colIndex = 0; colIndex < 15; colIndex++) {
                    char currentObjectCharacter = currentLine.charAt(colIndex);

                    Entity currentObject = generateEntity(currentObjectCharacter, rowIndex, colIndex);

                    if (currentObjectCharacter == 'p') {
                        players.add((Player) currentObject);
                        floorTiles.add(new Floor(colIndex, rowIndex));
                    } else if (currentObjectCharacter == 'f') {
                        floorTiles.add((Floor) currentObject);
                    } else if (currentObjectCharacter == 'w') {
                        wallTiles.add((Wall) currentObject);
                    }

                board[rowIndex][colIndex] = currentObject;
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
            case 'w' -> new Wall(colIndex, rowIndex);
            case 'c' -> new Chest(colIndex, rowIndex);
            case 'p' -> new Player(colIndex, rowIndex);
            case 'f' -> new Floor(colIndex, rowIndex);
            case 'm' -> new Monster(colIndex, rowIndex);
            default -> new Entity();
        };
    }

    public void changePlayerPosition() {
        for (Player p : players) {
            int playerX = p.getPosition().getX() / 48;
            int playerY = p.getPosition().getY() / 48;
            if(playerX != p.getBoardX() || playerY != p.getBoardY()) {
                Entity temp = board[p.getBoardY()][p.getBoardX()];
                board[p.getBoardY()][p.getBoardX()] = board[playerY][playerX];
                board[playerY][playerX] = temp;

                p.changeBoardPosition(playerX, playerY);
            }
        }
    }

    public void draw(Graphics2D g2) {
        changePlayerPosition();
        for(Floor f : floorTiles) {
            f.draw(g2);
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(!(board[i][j] instanceof Floor)){
                    board[i][j].draw(g2);
                }
            }
        }
    }

    public ArrayList<Wall> getWallTiles() {
        return this.wallTiles;
    }
}
