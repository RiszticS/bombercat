package org.ctrlaltdefeat.model;

import org.ctrlaltdefeat.controllers.configuration.ModelProperties;
import org.ctrlaltdefeat.controllers.game.RenderTimer;
import org.ctrlaltdefeat.model.fixedElements.*;
import org.ctrlaltdefeat.model.movingElements.Monster;
import org.ctrlaltdefeat.model.movingElements.Player;
import org.ctrlaltdefeat.model.positions.MatrixPosition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Level {
    private final FixedElement[][] board;
    private final ArrayList<Player> players;
    private final ArrayList<Monster> monsters;
    private final int playerNumberProvidedInTheMenu;
    private int numberOfPlayerPositionsInTheFile;
    private final RenderTimer winCountdown;
    private boolean draw, win, winCheckingInProgress;
    private Player winner;

    public Level(int levelNumber, int playerNumberProvidedInTheMenu) throws IOException {
        this.board = new FixedElement[15][15];
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.playerNumberProvidedInTheMenu = playerNumberProvidedInTheMenu;
        numberOfPlayerPositionsInTheFile = 0;
        this.winCountdown = new RenderTimer(300);
        this.draw = false;
        this.win = false;
        this.winCheckingInProgress = false;
        this.winner = null;

        ArrayList<Chest> chests = new ArrayList<>();
        readLevelFromFile(levelNumber, board, players, monsters, chests);
        allocatePowerUpsToRandomChests(chests);
    }

    private void readLevelFromFile(int levelNumber, FixedElement[][] board, ArrayList<Player> players,
                                   ArrayList<Monster> monsters, ArrayList<Chest> chests) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/levels/level" + levelNumber + ".txt"));

        String currentLine;

        int rowIndex = 0;
        while ((currentLine = reader.readLine()) != null) {
            for (int colIndex = 0; colIndex < 15; colIndex++) {
                char currentCharacter = currentLine.charAt(colIndex);

                if (currentCharacter == 'p') {
                    if (numberOfPlayerPositionsInTheFile < playerNumberProvidedInTheMenu) {
                        players.add(new Player(new MatrixPosition(rowIndex, colIndex)));
                    }
                    board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex));
                    numberOfPlayerPositionsInTheFile++;
                } else if (currentCharacter == 'm') {
                    monsters.add(new Monster(new MatrixPosition(rowIndex, colIndex)));
                    board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex));
                } else if (currentCharacter == 'f') {
                    board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex));
                } else if (currentCharacter == 'w') {
                    board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex));
                } else if (currentCharacter == 'c') {
                    Chest c = new Chest(new MatrixPosition(rowIndex, colIndex));
                    board[rowIndex][colIndex] = c;
                    chests.add(c);
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
        if (playersAliveCount() == 1) {
            if (!winCheckingInProgress) {
                winCountdown.start();
                winCheckingInProgress = true;
            } else {
                if (winCountdown.finished() && playersAliveCount() == 1) {
                    this.winner = players.getFirst();
                    win = true;
                } else {
                    winCountdown.decrease();
                }
            }
        } else if (players.isEmpty()) {
            draw = true;
        }
    }

    private void allocatePowerUpsToRandomChests(ArrayList<Chest> chests) {
        if (!chests.isEmpty()) {
            ArrayList<PowerUp> powerUps = generateRandomPowerUpsInRange(1, (int)Math.ceil((float) chests.size() / 3) );

            ArrayList<Integer> alreadyPickedIndices = new ArrayList<>();
            for (PowerUp p : powerUps) {
                int randomChestIndex = ThreadLocalRandom.current().nextInt(0, chests.size());
                while (alreadyPickedIndices.contains(randomChestIndex)) {
                    randomChestIndex = ThreadLocalRandom.current().nextInt(0, chests.size());
                }
                chests.get(randomChestIndex).addPowerUp(p);
                alreadyPickedIndices.add(randomChestIndex);
            }
        }
    }


    /**
     * Generates a random number of PowerUp objects of random PowerUp subtypes within the given range
     * and returns them in an ArrayList<>.
     * IMPORTANT: When new power up types are added, make sure to update the
     * number_of_types_of_power_ups attribute in the model.properties file, otherwise, the function is
     * going to ignore the new type. Obviously, also add a new case to the switch statement.
     * @param min An integer that represents the minimum number of power ups to be generated.
     * @param max An integer that represents the maximum number of power ups to be generated (inclusive).
     * @return An ArrayList of PowerUp objects.
     */
    private ArrayList<PowerUp> generateRandomPowerUpsInRange(int min, int max) {
        int numOfPowerUps = ThreadLocalRandom.current().nextInt(min, max + 1);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        for (int i = 0; i < numOfPowerUps; i++) {
            int typeOfPowerUp = ThreadLocalRandom.current().nextInt(1,
                    ModelProperties.getNumberOfTypesOfPowerUps() + 1);
            switch (typeOfPowerUp) {
                case 1:
                    powerUps.add(new PowerUpBombRange(new MatrixPosition(0,0)));
                    break;
                case 2:
                    powerUps.add(new PowerUpPlusBomb(new MatrixPosition(0, 0)));
            }
        }
        return powerUps;
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

    private int playersAliveCount() {
        int alive = 0;
        for (Player p : players) {
            if (p.isAlive()) {
                alive++;
            }
        }
        return alive;
    }
}
