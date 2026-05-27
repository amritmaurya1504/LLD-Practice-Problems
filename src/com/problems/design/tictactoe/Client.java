package com.problems.design.tictactoe;

import java.util.*;

class Symbol {
    private char mark;
    public Symbol(char mark){
        this.mark = mark;
    }

    public char getMark() {
        return mark;
    }
}

class Board {
    private ArrayList<ArrayList<Symbol>> grid;
    private int size;
    private Symbol emptyCell;

    public Board(int size){
        this.size = size;
        this.emptyCell = new Symbol('_');
        this.grid = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Symbol> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(emptyCell);
            }
            grid.add(row);
        }
    }

    public boolean isCellEmpty(int row, int col) {
        if(row < 0 || row >= size || col < 0 || col >= size) return false;
        return grid.get(row).get(col).equals(emptyCell);
    }

    public Symbol getCell(int row, int col){
        if(row < 0 || row >= size || col < 0 || col >= size) return emptyCell;
        return grid.get(row).get(col);
    }

    public boolean markInGrid(int row, int col, Symbol mark){
        if(row < 0 || row >= size || col < 0 || col >= size) return false;
        if(!isCellEmpty(row, col)) return false;
        grid.get(row).set(col, mark);
        return true;
    }

    public void display() {
        System.out.print("  ");
        for (int i = 0; i < size ; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < size ; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size ; j++) {
                System.out.print(grid.get(i).get(j).getMark() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }

    public Symbol getEmptyCell() {
        return emptyCell;
    }
}

interface TicTacToeRule {
    boolean isValidMove(Board board, int row, int col);
    boolean checkForWin(Board board, Symbol mark);
    boolean checkForDraw(Board board);
}

class StandardTicTacToeRule implements TicTacToeRule {
    @Override
    public boolean isValidMove(Board board, int row, int col) {
        return board.isCellEmpty(row,col);
    }

    @Override
    public boolean checkForWin(Board board, Symbol mark) {
        int size = board.getSize();

        // Check row;
        for (int i = 0; i < size ; i++) {
            boolean win = true;
            for (int j = 0; j < size; j++) {
                if(board.getCell(i,j).getMark() != mark.getMark()) {
                    win = false;
                    break;
                }
            }
            if(win) return true;
        }

        // Check columns
        for (int i = 0; i < size; i++) {
            boolean win = true;
            for (int j = 0; j < size ; j++) {
                if(board.getCell(j, i).getMark() != mark.getMark()){
                    win = false;
                    break;
                }
            }
            if(win) return true;
        }

        // Check Main Diagonal
        boolean win = true;
        for (int i = 0; i < size ; i++) {
            if(board.getCell(i, i).getMark() != mark.getMark()){
                win = false;
                break;
            }
        }
        if(win) return true;

        // Check Anti Diagonal
        win = true;
        for (int i = 0; i < size ; i++) {
            if(board.getCell(i, size - 1 - i).getMark() != mark.getMark()){
                win = false;
                break;
            }
        }

        return win;
    }

    @Override
    public boolean checkForDraw(Board board) {
        int size = board.getSize();
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                if(board.getCell(i,j).equals(board.getEmptyCell())){
                    return false;
                }
            }
        }
        return true;
    }
}

class Player {
    private int id;
    private String name;
    private Symbol mark;

    Player(int id, String name, Symbol mark){
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Symbol getMark() {
        return mark;
    }
}

interface Observer {
    void update(String message);
}

class ConsoleNotifier implements Observer {
    @Override
    public void update(String message) {
        System.out.println("[Notification] " + message);
    }
}

enum GameType {
    STANDARD, CUSTOM
}

class TicToeGameFactory {
    public static TicTaeToeGame createGame(GameType gt, int boardSize){
        TicTaeToeGame game = new TicTaeToeGame(boardSize);
        if(gt.equals(GameType.STANDARD)){
            game.setRule(new StandardTicTacToeRule());
            return game;
        }
        return null;
    }
}

class TicTaeToeGame {
    private Board board;
    private Deque<Player> players;
    private TicTacToeRule rule;
    private boolean gameOver;
    List<Observer> observers;

    TicTaeToeGame(int boardSize){
        this.board = new Board(boardSize);
        this.players = new ArrayDeque<>();
        this.rule = new StandardTicTacToeRule();
        this.observers = new ArrayList<>();
        this.gameOver = false;
    }

    public void addObserver (Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(String message){
        for (Observer ob : observers){
            ob.update(message);
        }
    }

    public void addPlayers(Player player){
        players.add(player);
    }

    public void setRule(TicTacToeRule ticTacToeRule){
        this.rule = ticTacToeRule;
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        if(players.size() < 2) {
            System.out.println("Need at least 2 players!");
            return;
        }

        notifyObservers("Tic Tac Toe Game Started!");

        while (true){
            board.display();

            // Take out the current player from dequeue
            Player currentPlayer = players.pollFirst();
            System.out.println(currentPlayer.getName() + "( " + currentPlayer.getMark().getMark() + " ) - Enter row and column: ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            // check if move is valid
            if(rule.isValidMove(board, row, col)){
                board.markInGrid(row,col, currentPlayer.getMark());
                notifyObservers(currentPlayer.getName() + " played (" + row + ", " + col + ")");

                if(rule.checkForWin(board, currentPlayer.getMark())){
                    board.display();
                    notifyObservers(currentPlayer.getName() + " wins!");
                    break;
                }

                if(rule.checkForDraw(board)) {
                    board.display();
                    notifyObservers("Game is draw!");
                    break;
                }

                // Move player to back of queue
                players.offerLast(currentPlayer);


            }else{
                System.out.println("Invalid mode! Try again.");
            }
        }
    }

}

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TIC TAC TOE GAME ===");

        // Create Game with custom board rule
        System.out.print("Enter the board size: ");
        int boardSize = scanner.nextInt();
        TicTaeToeGame game = TicToeGameFactory.createGame(GameType.STANDARD, boardSize);

        // Create and Add observer
        Observer observer = new ConsoleNotifier();
        assert game != null;
        game.addObserver(observer);

        // Create Players
        Player player1 = new Player(1, "Amrit", new Symbol('X'));
        Player player2 = new Player(2, "Sagrika", new Symbol('O'));

        game.addPlayers(player1);
        game.addPlayers(player2);

        game.play();

    }
}
