package main.models;

import main.controllers.configuration.GraphicProperties;
import main.models.entities.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
    private final Entity[][] board;
    private final ArrayList<Player> players;
    private final ArrayList<Floor> floorTiles;
    private final ArrayList<Wall> wallTiles;
    private final ArrayList<Chest> chestTiles;
    private ArrayList<Monster> monsters;
    private ArrayList<PowerUp> powerUps;
    private PlusBomb p1;
    private ExtendedExplosion exe;

    public Level(int levelNumber) throws IOException {
        players = new ArrayList<>();
        monsters = new ArrayList<>();
        powerUps = new ArrayList<>();
        p1 = new PlusBomb(1, 1);
        powerUps.add(p1);
        exe = new ExtendedExplosion(10, 1);
        powerUps.add(exe);
        try {
            BufferedReader  reader = new BufferedReader(new FileReader("src/main/assets/levels/level" + levelNumber + ".txt"));

            String currentLine;

            board = new Entity[15][15];
            floorTiles = new ArrayList<>();
            wallTiles = new ArrayList<>();
            chestTiles = new ArrayList<>();
            int rowIndex = 0;
            while((currentLine = reader.readLine()) != null) {
                for(int colIndex = 0; colIndex < 15; colIndex++) {
                    char currentObjectCharacter = currentLine.charAt(colIndex);

                    Entity currentObject = generateEntity(currentObjectCharacter, rowIndex, colIndex);

                    if (currentObjectCharacter == 'p') {
                        players.add((Player) currentObject);
                        floorTiles.add(new Floor(colIndex, rowIndex));
                    }else if (currentObjectCharacter == 'm') {
                        monsters.add((Monster) currentObject);
                        floorTiles.add(new Floor(colIndex, rowIndex));
                    }else if (currentObjectCharacter == 'f') {
                        floorTiles.add((Floor) currentObject);
                    } else if (currentObjectCharacter == 'w') {
                        wallTiles.add((Wall) currentObject);
                    } else if (currentObjectCharacter == 'c') {
                        chestTiles.add((Chest) currentObject);
                        floorTiles.add(new Floor(colIndex, rowIndex));
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

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
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

    public void changeEntityPosition(ArrayList<? extends Entity> entities) {
        for (Entity entity : entities) {
            int entityX = entity.getHitbox().getX() / GraphicProperties.getTileSize();
            int entityY = entity.getHitbox().getY() / GraphicProperties.getTileSize();
            if(entityX != entity.getBoardX() || entityY != entity.getBoardY()) {
                Entity temp = board[entity.getBoardY()][entity.getBoardX()];
                board[entity.getBoardY()][entity.getBoardX()] = board[entityY][entityX];
                board[entityY][entityX] = temp;

                entity.changeBoardPosition(entityX, entityY);
            }
        }
    }

    public void removePowerUp(){
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i) != null && powerUps.get(i).isPickedUp()) {
                powerUps.set(i, null);
                powerUps.remove(i);
            }
        }
    }

    public void draw(Graphics2D g2) {
        changeEntityPosition(players);
        changeEntityPosition(monsters);
        for(Floor f : floorTiles) {
            f.draw(g2);
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(!(board[i][j] instanceof Floor)) {
                    board[i][j].draw(g2);
                }
            }
        }
        if(!powerUps.isEmpty()){
            for (int i = 0; i < powerUps.size(); i++) {
                if (powerUps.get(i) != null) {
                    powerUps.get(i).draw(g2);
                    if(powerUps.get(i).isPickedUp()){
                        powerUps.set(i, null);
                        powerUps.remove(i);
                    }
                }
            }
        }


    }

    public ArrayList<Wall> getWallTiles() {
        return this.wallTiles;
    }

    public ArrayList<Chest> getChestTiles() {
        return chestTiles;
    }
}
