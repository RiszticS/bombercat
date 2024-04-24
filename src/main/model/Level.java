package main.model;

import main.controllers.game.RenderTimer;
import main.model.fixedElements.*;
import main.model.movingElements.Monster;
import main.model.movingElements.MovingElement;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Level {
    //private final FixedElement[][] board;
    private final FixedElement[][] board = {
            {new Wall(new MatrixPosition(0, 0)), new Wall(new MatrixPosition(0, 1)), new Wall(new MatrixPosition(0, 2)), new Wall(new MatrixPosition(0, 3)), new Wall(new MatrixPosition(0, 4)), new Wall(new MatrixPosition(0, 5)), new Wall(new MatrixPosition(0, 6)), new Wall(new MatrixPosition(0, 7)), new Wall(new MatrixPosition(0, 8)), new Wall(new MatrixPosition(0, 9)), new Wall(new MatrixPosition(0, 10)), new Wall(new MatrixPosition(0, 11)), new Wall(new MatrixPosition(0, 12)), new Wall(new MatrixPosition(0, 13)), new Wall(new MatrixPosition(0, 14))},
            {new Wall(new MatrixPosition(1, 0)), new EmptyTile(new MatrixPosition(1, 1)), new EmptyTile(new MatrixPosition(1, 2)), new EmptyTile(new MatrixPosition(1, 3)), new EmptyTile(new MatrixPosition(1, 4)), new EmptyTile(new MatrixPosition(1, 5)), new EmptyTile(new MatrixPosition(1, 6)), new EmptyTile(new MatrixPosition(1, 7)), new EmptyTile(new MatrixPosition(1, 8)), new EmptyTile(new MatrixPosition(1, 9)), new EmptyTile(new MatrixPosition(1, 10)), new EmptyTile(new MatrixPosition(1, 11)), new EmptyTile(new MatrixPosition(1, 12)), new EmptyTile(new MatrixPosition(1, 13)), new Wall(new MatrixPosition(1, 14))},
            {new Wall(new MatrixPosition(2, 0)), new EmptyTile(new MatrixPosition(2, 1)), new Wall(new MatrixPosition(2, 2)), new EmptyTile(new MatrixPosition(2, 3)), new Wall(new MatrixPosition(2, 4)), new EmptyTile(new MatrixPosition(2, 5)), new Wall(new MatrixPosition(2, 6)), new EmptyTile(new MatrixPosition(2, 7)), new Wall(new MatrixPosition(2, 8)), new EmptyTile(new MatrixPosition(2, 9)), new Wall(new MatrixPosition(2, 10)), new EmptyTile(new MatrixPosition(2, 11)), new Wall(new MatrixPosition(2, 12)), new EmptyTile(new MatrixPosition(2, 13)), new Wall(new MatrixPosition(2, 14))},
            {new Wall(new MatrixPosition(3, 0)), new EmptyTile(new MatrixPosition(3, 1)), new EmptyTile(new MatrixPosition(3, 2)), new EmptyTile(new MatrixPosition(3, 3)), new EmptyTile(new MatrixPosition(3, 4)), new EmptyTile(new MatrixPosition(3, 5)), new EmptyTile(new MatrixPosition(3, 6)), new EmptyTile(new MatrixPosition(3, 7)), new EmptyTile(new MatrixPosition(3, 8)), new EmptyTile(new MatrixPosition(3, 9)), new EmptyTile(new MatrixPosition(3, 10)), new EmptyTile(new MatrixPosition(3, 11)), new EmptyTile(new MatrixPosition(3, 12)), new EmptyTile(new MatrixPosition(3, 13)), new Wall(new MatrixPosition(3, 14))},
            {new Wall(new MatrixPosition(4, 0)), new EmptyTile(new MatrixPosition(4, 1)), new Wall(new MatrixPosition(4, 2)), new EmptyTile(new MatrixPosition(4, 3)), new Wall(new MatrixPosition(4, 4)), new EmptyTile(new MatrixPosition(4, 5)), new Wall(new MatrixPosition(4, 6)), new EmptyTile(new MatrixPosition(4, 7)), new Wall(new MatrixPosition(4, 8)), new EmptyTile(new MatrixPosition(4, 9)), new Wall(new MatrixPosition(4, 10)), new EmptyTile(new MatrixPosition(4, 11)), new Wall(new MatrixPosition(4, 12)), new EmptyTile(new MatrixPosition(4, 13)), new Wall(new MatrixPosition(4, 14))},
            {new Wall(new MatrixPosition(5, 0)), new EmptyTile(new MatrixPosition(5, 1)), new EmptyTile(new MatrixPosition(5, 2)), new Chest(new MatrixPosition(5, 3)), new EmptyTile(new MatrixPosition(5, 4)), new EmptyTile(new MatrixPosition(5, 5)), new EmptyTile(new MatrixPosition(5, 6)), new EmptyTile(new MatrixPosition(5, 7)), new EmptyTile(new MatrixPosition(5, 8)), new EmptyTile(new MatrixPosition(5, 9)), new EmptyTile(new MatrixPosition(5, 10)), new EmptyTile(new MatrixPosition(5, 11)), new EmptyTile(new MatrixPosition(5, 12)), new EmptyTile(new MatrixPosition(5, 13)), new Wall(new MatrixPosition(5, 14))},
            {new Wall(new MatrixPosition(6, 0)), new EmptyTile(new MatrixPosition(6, 1)), new Wall(new MatrixPosition(6, 2)), new EmptyTile(new MatrixPosition(6, 3)), new Wall(new MatrixPosition(6, 4)), new EmptyTile(new MatrixPosition(6, 5)), new Wall(new MatrixPosition(6, 6)), new EmptyTile(new MatrixPosition(6, 7)), new Wall(new MatrixPosition(6, 8)), new EmptyTile(new MatrixPosition(6, 9)), new Wall(new MatrixPosition(6, 10)), new EmptyTile(new MatrixPosition(6, 11)), new Wall(new MatrixPosition(6, 12)), new EmptyTile(new MatrixPosition(6, 13)), new Wall(new MatrixPosition(6, 14))},
            {new Wall(new MatrixPosition(7, 0)), new EmptyTile(new MatrixPosition(7, 1)), new EmptyTile(new MatrixPosition(7, 2)), new EmptyTile(new MatrixPosition(7, 3)), new EmptyTile(new MatrixPosition(7, 4)), new EmptyTile(new MatrixPosition(7, 5)), new EmptyTile(new MatrixPosition(7, 6)), new EmptyTile(new MatrixPosition(7, 7)), new EmptyTile(new MatrixPosition(7, 8)), new EmptyTile(new MatrixPosition(7, 9)), new EmptyTile(new MatrixPosition(7, 10)), new EmptyTile(new MatrixPosition(7, 11)), new EmptyTile(new MatrixPosition(7, 12)), new EmptyTile(new MatrixPosition(7, 13)), new Wall(new MatrixPosition(7, 14))},
            {new Wall(new MatrixPosition(8, 0)), new EmptyTile(new MatrixPosition(8, 1)), new Wall(new MatrixPosition(8, 2)), new EmptyTile(new MatrixPosition(8, 3)), new Wall(new MatrixPosition(8, 4)), new EmptyTile(new MatrixPosition(8, 5)), new Wall(new MatrixPosition(8, 6)), new EmptyTile(new MatrixPosition(8, 7)), new Wall(new MatrixPosition(8, 8)), new EmptyTile(new MatrixPosition(8, 9)), new Wall(new MatrixPosition(8, 10)), new EmptyTile(new MatrixPosition(8, 11)), new Wall(new MatrixPosition(8, 12)), new EmptyTile(new MatrixPosition(8, 13)), new Wall(new MatrixPosition(8, 14))},
            {new Wall(new MatrixPosition(9, 0)), new EmptyTile(new MatrixPosition(9, 1)), new EmptyTile(new MatrixPosition(9, 2)), new PowerUp(new MatrixPosition(9, 3)), new EmptyTile(new MatrixPosition(9, 4)), new EmptyTile(new MatrixPosition(9, 5)), new EmptyTile(new MatrixPosition(9, 6)), new EmptyTile(new MatrixPosition(9, 7)), new EmptyTile(new MatrixPosition(9, 8)), new EmptyTile(new MatrixPosition(9, 9)), new EmptyTile(new MatrixPosition(9, 10)), new EmptyTile(new MatrixPosition(9, 11)), new EmptyTile(new MatrixPosition(9, 12)), new EmptyTile(new MatrixPosition(9, 13)), new Wall(new MatrixPosition(9, 14))},
            {new Wall(new MatrixPosition(10, 0)), new EmptyTile(new MatrixPosition(10, 1)), new Wall(new MatrixPosition(10, 2)), new EmptyTile(new MatrixPosition(10, 3)), new Wall(new MatrixPosition(10, 4)), new EmptyTile(new MatrixPosition(10, 5)), new Wall(new MatrixPosition(10, 6)), new EmptyTile(new MatrixPosition(10, 7)), new Wall(new MatrixPosition(10, 8)), new EmptyTile(new MatrixPosition(10, 9)), new Wall(new MatrixPosition(10, 10)), new EmptyTile(new MatrixPosition(10, 11)), new Wall(new MatrixPosition(10, 12)), new EmptyTile(new MatrixPosition(10, 13)), new Wall(new MatrixPosition(10, 14))},
            {new Wall(new MatrixPosition(11, 0)), new EmptyTile(new MatrixPosition(11, 1)), new EmptyTile(new MatrixPosition(11, 2)), new EmptyTile(new MatrixPosition(11, 3)), new EmptyTile(new MatrixPosition(11, 4)), new EmptyTile(new MatrixPosition(11, 5)), new EmptyTile(new MatrixPosition(11, 6)), new EmptyTile(new MatrixPosition(11, 7)), new EmptyTile(new MatrixPosition(11, 8)), new EmptyTile(new MatrixPosition(11, 9)), new EmptyTile(new MatrixPosition(11, 10)), new EmptyTile(new MatrixPosition(11, 11)), new EmptyTile(new MatrixPosition(11, 12)), new EmptyTile(new MatrixPosition(11, 13)), new Wall(new MatrixPosition(11, 14))},
            {new Wall(new MatrixPosition(12, 0)), new EmptyTile(new MatrixPosition(12, 1)), new Wall(new MatrixPosition(12, 2)), new EmptyTile(new MatrixPosition(12, 3)), new Wall(new MatrixPosition(12, 4)), new EmptyTile(new MatrixPosition(12, 5)), new Wall(new MatrixPosition(12, 6)), new EmptyTile(new MatrixPosition(12, 7)), new Wall(new MatrixPosition(12, 8)), new EmptyTile(new MatrixPosition(12, 9)), new Wall(new MatrixPosition(12, 10)), new EmptyTile(new MatrixPosition(12, 11)), new Wall(new MatrixPosition(12, 12)), new EmptyTile(new MatrixPosition(12, 13)), new Wall(new MatrixPosition(12, 14))},
            {new Wall(new MatrixPosition(13, 0)), new EmptyTile(new MatrixPosition(13, 1)), new EmptyTile(new MatrixPosition(13, 2)), new EmptyTile(new MatrixPosition(13, 3)), new EmptyTile(new MatrixPosition(13, 4)), new EmptyTile(new MatrixPosition(13, 5)), new EmptyTile(new MatrixPosition(13, 6)), new EmptyTile(new MatrixPosition(13, 7)), new EmptyTile(new MatrixPosition(13, 8)), new EmptyTile(new MatrixPosition(13, 9)), new EmptyTile(new MatrixPosition(13, 10)), new EmptyTile(new MatrixPosition(13, 11)), new EmptyTile(new MatrixPosition(13, 12)), new EmptyTile(new MatrixPosition(13, 13)), new Wall(new MatrixPosition(13, 14))},
            {new Wall(new MatrixPosition(14, 0)), new Wall(new MatrixPosition(14, 1)), new Wall(new MatrixPosition(14, 2)), new Wall(new MatrixPosition(14, 3)), new Wall(new MatrixPosition(14, 4)), new Wall(new MatrixPosition(14, 5)), new Wall(new MatrixPosition(14, 6)), new Wall(new MatrixPosition(14, 7)), new Wall(new MatrixPosition(14, 8)), new Wall(new MatrixPosition(14, 9)), new Wall(new MatrixPosition(14, 10)), new Wall(new MatrixPosition(14, 11)), new Wall(new MatrixPosition(14, 12)), new Wall(new MatrixPosition(14, 13)), new Wall(new MatrixPosition(14, 14))},
    };
    private final ArrayList<Player> players;
    private final ArrayList<Monster> monsters;
    private final ArrayList<PowerUp> powerUps;
    private final ArrayList<MovingElement> deadElements;
    private final int playerNumber;
    private int playersCount;
    private RenderTimer winCountdown;
    private boolean draw, win, winCheckingInProgress;
    private Player winner;

    public Level(int levelNumber, int playerNumber) throws IOException {
        //board = new FixedElement[15][15];
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.deadElements = new ArrayList<>();
        this.playerNumber = playerNumber;
        this.winCountdown = new RenderTimer(300);
        this.draw = false;
        this.win = false;
        this.winCheckingInProgress = false;
        this.winner = null;

        players.add(new Player(new MatrixPosition(1,1)));
        players.add(new Player(new MatrixPosition(1, 3)));
        players.add(new Player(new MatrixPosition(1, 5)));

        monsters.add(new Monster(new MatrixPosition(5,5)));

        /*try {
            readLevelFromFile(levelNumber, board, players, monsters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private void readLevelFromFile(int levelNumber, FixedElement[][] board, ArrayList<Player> players, ArrayList<Monster> monsters) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/assets/levels/level" + levelNumber + ".txt"));

        String currentLine;

        int rowIndex = 0;
        while ((currentLine = reader.readLine()) != null) {
            for (int colIndex = 0; colIndex < 15; colIndex++) {
                char currentCharacter = currentLine.charAt(colIndex);

                if (currentCharacter == 'p') {
                    players.add(new Player(new MatrixPosition(rowIndex, colIndex)));
                    playersCount++;
                } else if (currentCharacter == 'm') {
                    monsters.add(new Monster(new MatrixPosition(rowIndex, colIndex)));
                } else if (currentCharacter == 'f') {
                    board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex));
                } else if (currentCharacter == 'w') {
                    board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex));
                } else if (currentCharacter == 'c') {
                    board[rowIndex][colIndex] = new Chest(new MatrixPosition(rowIndex, colIndex));
                }
            }
            rowIndex++;
        }
    }

    public void update() {
        updateBoard();
        updateMonsters();
        updatePlayers();
        removeDeadMonsters();
        removeDeadPlayers();
        checkForWin();
        //System.out.println(players.size());
    }

    private void updateBoard() {
        for (FixedElement[] fixedBoardTiles : board) {
            for (int j = 0; j < board.length; j++) {
                fixedBoardTiles[j].update(board);
            }
        }
    }

    private void updateMonsters() {
        for (Monster monster : monsters) {
            monster.update(board);
        }
    }

    private void updatePlayers() {
        for (Player player : players) {
            player.update(board, monsters);
        }
    }
    private void removeDeadPlayers() {
        Predicate<Player> dead = p -> !p.isAlive();
        players.removeIf( dead );
    }

    private void removeDeadMonsters() {
        Predicate<Monster> dead = p -> !p.isAlive();
        monsters.removeIf( dead );
    }

    private void checkForWin() {
        if (players.size() == 1) {
            if (!winCheckingInProgress) {
                winCountdown.start();
                winCheckingInProgress = true;
            } else {
                if (winCountdown.finished() && players.size() == 1) {
                    this.winner = players.getFirst();
                } else {
                    winCountdown.decrease();
                }
            }
        } else if (players.isEmpty()) {
            draw = true;
        }
    }



    public void draw(Graphics2D g2) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j].draw(g2);
            }
        }
        for (Monster monster : monsters) {
            monster.draw(g2);
        }
        for (Player player : players) {
            player.draw(g2);
        }
    }

    public boolean isDraw() {
        return draw;
    }

    public boolean isWin() {
        return win;
    }

    public Player getWinner() {
        return winner;
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
}
