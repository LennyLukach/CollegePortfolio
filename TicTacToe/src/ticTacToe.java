//https://en.wikipedia.org/wiki/Tic-tac-toe - make perfect AI

import java.util.Scanner;
import java.util.ArrayList;

class ticTacToe {
    public static void main(String[] args)  {
        
        ArrayList<Location> locations = new ArrayList<Location>();
        Scanner in = new Scanner(System.in);
        String board[][] = new String[3][3];
        boolean gameActive = true;
        int playerTurn = (int) Math.round(Math.random());


        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                board[x][y] = ".";
                locations.add(new Location(x, y, false));
            }
        }

        int Ppos = -10;
        while (gameActive) {
            if (isDraw(board)) {
                System.out.println("It's a draw!");
                break;
            }
            printBoard(board);
            if (playerTurn == 1) {
                int pos = in.nextInt();
                Ppos = pos;
                
                int x = locations.get(pos - 1).getX();
                int y = locations.get(pos - 1).getY();

                if (locations.get(pos - 1).used) {
                    System.out.println("You can't go there. For being that stupid, you lose your turn.");
                }
                else {
                    locations.get(pos - 1).used = true;
                    board[x][y] = "x";
                }
                if (checkCol(board, "x") || checkRow(board, "x") || checkDiag(board, "x")) {
                    System.out.println("Player wins!");
                    break;
                }
                playerTurn = 0;
            }
            else if (playerTurn == 0) {
                while (true) {
                    int pos = getBestMove(board, locations, Ppos);
                    if (!(pos >= 0 && pos < 10)) {
                        pos = (int) (Math.random() *locations.size());
                    }
                    if (locations.get(pos).used == false) {
                        int x = locations.get(pos).getX();
                        int y = locations.get(pos).getY();
                        locations.get(pos).used = true;
                        board[x][y] = "o";
                        break;
                    }
                }

                if (checkCol(board, "o") || checkRow(board, "o") || checkDiag(board, "o")) {
                    System.out.println("Computer wins!");
                    break;
                }
                playerTurn = 1;
            }
            System.out.println();

        }

        printBoard(board);
        in.close();
    }

    public static void printBoard(String[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                System.out.print(board[x][y] +  " ");
            }
            System.out.println();
        }
    }

    public static boolean checkRow(String[][] board, String icon) {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == icon) {
                    count++;
                }
            }
            if  (count == 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDiag(String[][] board, String icon) {
        if (board[0][0] == icon && board[1][1] == icon && board[2][2] == icon) {
            return true;
        }
        if (board[0][2] == icon && board[1][1] == icon && board[2][0] == icon) {
            return true;
        }
        return false;
    }

    public static boolean checkCol(String[][] board, String icon) {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (int y = 0; y < board[0].length; y++) {
                if (board[y][x] == icon) {
                    count++;
                }
            }
            if  (count == 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw(String[][] board) {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != ".") {
                    count++;
                }
            }
        }
        if (count == 9) {
            return true;
        }
        return false;
    }

    public static int getBestMove(String[][] board, ArrayList<Location> locations, int playerPos) {
        int count = 0;
        int movePos = -1;
        int tempMove = -1;
        int temp = 0;

        //!Move opposite corner
        if (playerPos > -1) {
            int botX;
            int botY;
            int playerX = locations.get(playerPos - 1).x;
            int playerY = locations.get(playerPos - 1).y;
            if (playerX == 0 || playerX == 2) {
                if (playerY == 0 || playerY == 2) {
                    if (playerX == 0) {
                        botX = 2;
                    }
                    else {
                        botX = 0;
                    }
                    if (playerY == 0) {
                        botY = 2;
                    }
                    else {
                        botY = 0;
                    }
                    for (int x = 0; x < locations.size(); x++) {
                        if (locations.get(x).x == botX && locations.get(x).y == botY) {
                            if (board[botX][botY] == ".") {
                                movePos = x;
                               // System.out.println("corner");
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            }
        }


        //!Check opponent horizontal win
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == "x")  {
                    count++;
                }
                else if (board[x][y] == ".") {
                    tempMove = findLocation(locations, temp, x, y);
                }
                else if (board[x][y] == "o") {
                    count -= 10;
                }
            }
            if (count >= 2) {
                movePos = tempMove;
                //System.out.println("opp hor");
                break;
            }
        }

        //!Check opponent vertical win
        count = 0;
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (int y = 0; y < board[0].length; y++) {
                if (board[y][x] == "x") {
                    count++;
                }
                else if (board[y][x] == ".") {
                    tempMove = findLocation(locations, temp, y, x);
                }
                else if (board[y][x] == "o") {
                    count -= 10;
                }
            }
            if (count >= 2) {
                //System.out.println("opp vert");
                movePos = tempMove;
                break;
            }
        }

        //!Check opponent right diagonal win \
        count = 0;
        for (int x = 0; x < board.length; x++) {
            int y = x;
            if (board[x][y] == "x") {
                count++;
            }
            else if (board[x][y] == ".") {
                tempMove = findLocation(locations, temp, x, y);
            }
            else if (board[x][y] == "o") {
                count -= 10;
            }
        }
        if (count >= 2) {
            //System.out.println("opp right");
            movePos = tempMove;
        }

        //!Check opponent left diagonal win /
        count = 0;
        int y = 0;
        for (int x = 2; x >= 0; x--) {
            if (board[x][y] == "x") {
                count++;
            }
            else if (board[x][y] == ".") {
                tempMove = findLocation(locations, temp, x, y);
            }
            else if (board[x][y] == "o") {
                count -= 10;
            }
            y++;
        }

        if (count >= 2) {
            //System.out.println("opp left");
            movePos = tempMove;
        }


        //!Check horizontal win
        count = 0;
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (y = 0; y < board[0].length; y++) {
                if (board[x][y] == "o")  {
                    count++;
                }
                else if (board[x][y] == ".") {
                    tempMove = findLocation(locations, temp, x, y);
                }
                else if (board[x][y] == "x") {
                    count -= 10;
                }
            }
            if (count >= 2) {
                //System.out.println("hor");
                movePos = tempMove;
                break;
            }
        }

        //!Check vertical win
        count = 0;
        for (int x = 0; x < board.length; x++) {
            count = 0;
            for (y = 0; y < board[0].length; y++) {
                if (board[y][x] == "o") {
                    count++;
                }
                else if (board[y][x] == ".") {
                    tempMove = findLocation(locations, temp, y, x);
                }
                else if (board[y][x] == "x") {
                    count -= 10;
                }
            }
            if (count >= 2) {
                //System.out.println("vert");
                movePos = tempMove;
                break;
            }
        }


        //!Check right diagonal win \
        count = 0;
        for (int x = 0; x < board.length; x++) {
            y = x;
            if (board[x][y] == "o") {
                count++;
            }
            else if (board[x][y] == ".") {
                tempMove = findLocation(locations, temp, x, y);
            }
            else if (board[x][y] == "x") {
                count -= 10;
            }
        }
        if (count >= 2) {
            //System.out.println("right");
            movePos = tempMove;
            return movePos;
        }

        //!Check left diagonal win /
        count = 0;
        y = 0;
        for (int x = 2; x >= 0; x--) {
            if (board[x][y] == "o") {
                count++;
            }
            else if (board[x][y] == ".") {
                tempMove = findLocation(locations, temp, x, y);
            }
            else if (board[x][y] == "x") {
                count -= 10;
            }
            y++;
        }

        if (count >= 2) {
            //System.out.println("left");
            movePos = tempMove;
            return movePos;
        }

        return movePos;
    }

    public static int findLocation(ArrayList<Location> locations, int temp, int x, int y) {
        int tempMove = -1;
        for (temp = 0; temp < locations.size(); temp++) {
            if (locations.get(temp).x == x && locations.get(temp).y == y) {
                tempMove = temp;
            }
        }
        return tempMove;
    }


}

class Location {

    int x;
    int y;
    boolean used;

    Location(int x, int y, boolean used) {
        this.x = x;
        this.y = y;
        this.used = used;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}