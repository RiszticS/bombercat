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
    public Level(int levelNumber) throws IOException {
        players = new ArrayList<>();
        try {
            BufferedReader  reader = new BufferedReader(new FileReader("src/assets/levels/level" + levelNumber + ".txt"));

            String currentLine;

            board = new Entity[15][15];
            floorTiles = new ArrayList<>();
            int rowIndex = 0;
            while((currentLine = reader.readLine()) != null) {
                for(int colIndex = 0; colIndex < 15; colIndex++) {
                    char currentObjectCharacter = currentLine.charAt(colIndex);

                    Entity currentObject = generateEntity(currentObjectCharacter, rowIndex, colIndex);

                    board[rowIndex][colIndex] = currentObject;

                    if (currentObjectCharacter == 'p') {
                        players.add((Player) currentObject);
                        floorTiles.add(new Floor(rowIndex, colIndex));
                    } else if (currentObjectCharacter == 'f') {
                        floorTiles.add((Floor) currentObject);
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
            case 'p' -> new Player(rowIndex, colIndex);
            case 'f' -> new Floor(rowIndex, colIndex);
            case 'm' -> new Monster(rowIndex, colIndex);
            default -> new Entity();
        };
    }

    public void changePlayerPosition() {
        for (Player p : players) {
            int playerX = p.getPosition().getX() / 48;
            int playerY = p.getPosition().getY() / 48;
            if(!(board[playerX][playerY] instanceof Player)) {
                Entity temp = board[p.getBoardX()][p.getBoardY()];
                board[p.getBoardX()][p.getBoardY()] = board[playerX][playerY];
                board[playerX][playerY] = temp;

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
                if(board[i][j] instanceof Floor) {
                    continue;
                }

                board[i][j].draw(g2);

                if(board[i][j] instanceof  Player) {
                    System.out.println(board[i][j].getPosition().getX() + ", " + board[i][j].getPosition().getY());
                }
            }
        }
    }
}
