import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TicTac {
    private static Scanner scan = new Scanner(System.in);
    private static Random random = new Random();
    public static boolean playAgain(){
        System.out.print("Do u want to play Again (y/n): ");
        char ans;
        ans = scan.next().toCharArray()[0];
        ans = Character.toUpperCase(ans);
        if(ans == 'Y') return true;
        else return false;
    }
    public static void prBoard(char[] board){
        System.out.print(" "+ board[7] + " | " + board[8] +" | "+ board[9] + " \n" + "---|---|---");
        System.out.print(" "+ board[4] + " | " + board[5] +" | "+ board[6] + " \n" + "---|---|---");
        System.out.print(" "+ board[1] + " | " + board[2] +" | "+ board[3] + " ");
    }
    public static char[] playerLetter(){
        char letter = ' ';
        char[] a = {'X','O'};
        char[] b = {'O','X'};
        System.out.print("What do u want X or O : ");
        while (!(letter == 'X' || letter == 'O')){
            letter = scan.next().toCharArray()[0];
            letter = Character.toUpperCase(letter);
        }
        if (letter == 'X') return a;
        else return b;
    }
    public static String whofirst(){
        int a = (int)(Math.random() + 0.3);
        if(a == 0) return  "computer";
        else return "player";
    }
    public static boolean isWinner(char[] bo, char le){
        return ((bo[7] == le && bo[8] == le && bo[9] == le) ||
                (bo[4] == le && bo[5] == le && bo[6] == le) ||
                (bo[1] == le && bo[2] == le && bo[3] == le) ||
                (bo[7] == le && bo[4] == le && bo[1] == le) ||
                (bo[8] == le && bo[5] == le && bo[2] == le) ||
                (bo[9] == le && bo[6] == le && bo[3] == le) ||
                (bo[7] == le && bo[5] == le && bo[3] == le) ||
                (bo[9] == le && bo[5] == le && bo[1] == le));
    }
    public static boolean isfree(char[] board,int move){
        if(board[move] == '\u0000') return true;
        else return false;

    }
    public static int getPlayerMove(char[] board){
        int mv;
        do{
            System.out.print("Whats the next move(1-9) : ");
            mv = scan.nextInt();
        }while ((mv >10 || mv  < 0) || !isfree(board,mv));
        return mv;
    }
    public static int chooseRandom(char[] board,int[] mvlist){
        ArrayList<Integer> posmv = new ArrayList<Integer>();
        for (int i : mvlist) if(isfree(board,i)) posmv.add(i);
        if(posmv.size() != 0){
            int k = random.nextInt(posmv.size());
            return posmv.get(k);
        }
        else return -1;
    }
    public static int getCompMove(char[] board,char cletter){
        char pletter;
        int[] p = {1,3,7,9};
        int[] q = {2,4,6,8};
        if(cletter == 'X') pletter = 'O';
        else pletter = 'X';
        for (int i = 1;i <= 9;i++){
            char[] cp = board.clone();
            if(isfree(cp,i)){
                cp[i] = cletter;
                if(isWinner(cp,cletter)) return i;
            }
        }
        for (int i = 1;i <= 9;i++){
            char[] cp = board.clone();
            if(isfree(cp,i)){
                cp[i] = pletter;
                if(isWinner(cp,pletter)) return i;
            }
        }
        if(isfree(board,5)) return 5;

        if ((board[5] == cletter) && (board[1] == '\u0000') && board[3] == '\u0000' && board[7] == '\u0000' && board[9] == '\u0000'){
            int mv = chooseRandom(board,p);
            if(mv != -1) return mv;
            return chooseRandom(board,q);
        }
        if(board[5] == cletter){
            int mv = chooseRandom(board,q);
            if(mv != -1) return mv;
            return chooseRandom(board,p);
        }else {
            int mv = chooseRandom(board,p);
            if(mv != -1) return mv;
            return chooseRandom(board,q);
        }
    }
    public static boolean isFull(char[] board){
        for (int i = 1; i<= 9 ; i++) if(isfree(board,i)) return false;
        return true;
    }
    public static char[] board;
    public static void main(String[] argc){
        System.out.println("welcome to Tic-Tac Toe Game !!");
        while (true){
            board = new char[10];
            char[] playerChars = playerLetter();
            char pletter,cletter;
            pletter = playerChars[0];
            cletter = playerChars[1];
            String turn = whofirst();
            System.out.println("The " + turn + " goes first !!");
            boolean running = true;
            while (running){
                int mv;
                if(turn.equals("player")){
                    mv = getPlayerMove(board);
                    board[mv] = pletter;
                    prBoard(board);
                    if(isWinner(board,pletter)){
                        prBoard(board);
                        System.out.println("Congrates you beat comp.");
                        running = false;
                    }else {
                        if(isFull(board)){
                            prBoard(board);
                            System.out.println("The game is a tie !!");
                            break;
                        }else turn = "computer";
                    }
                }else {
                    mv = getCompMove(board,cletter);
                    board[mv] = cletter;
                    System.out.println("computer's turn : ");
                    prBoard(board);
                    if (isWinner(board,cletter)){
                        prBoard(board);
                        System.out.println("U lost the game !!");
                        running = false;
                    }else {
                        if(isFull(board)){
                            prBoard(board);
                            System.out.println("The game is a Tie !!");
                            break;
                        }else turn = "player";
                    }
                }
            }if(!playAgain()) break;
        }
    }
}
