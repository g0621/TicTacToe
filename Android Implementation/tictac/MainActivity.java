package com.example.gyan.tictac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public char[] board = new char[10];
    public boolean gameplay = true;
    public char activeplayer;
    public Random random = new Random();

    public char whofirst() {
        int a = (int) (Math.random() * 20 + 1);
        if (a <= 10) return 'X';
        else return 'O';
    }

    public boolean isWinner(char[] bo, char le) {
        return ((bo[7] == le && bo[8] == le && bo[9] == le) ||
                (bo[4] == le && bo[5] == le && bo[6] == le) ||
                (bo[1] == le && bo[2] == le && bo[3] == le) ||
                (bo[7] == le && bo[4] == le && bo[1] == le) ||
                (bo[8] == le && bo[5] == le && bo[2] == le) ||
                (bo[9] == le && bo[6] == le && bo[3] == le) ||
                (bo[7] == le && bo[5] == le && bo[3] == le) ||
                (bo[9] == le && bo[5] == le && bo[1] == le));
    }

    public boolean isfree(char[] board, int move) {
        if (board[move] == '\u0000') return true;
        else return false;
    }

    public int chooseRandom(char[] board, int[] mvlist) {
        ArrayList<Integer> posmv = new ArrayList<Integer>();
        for (int i : mvlist) if (isfree(board, i)) posmv.add(i);
        if (posmv.size() != 0) {
            int k = random.nextInt(posmv.size());
            return posmv.get(k);
        } else return -1;
    }

    public int getCompMove(char[] board, char cletter) {
        char pletter;
        int[] p = {1, 3, 7, 9};
        int[] q = {2, 4, 6, 8};
        if (cletter == 'X') pletter = 'O';
        else pletter = 'X';
        for (int i = 1; i <= 9; i++) {
            char[] cp = board.clone();
            if (isfree(cp, i)) {
                cp[i] = cletter;
                if (isWinner(cp, cletter)) return i;
            }
        }
        for (int i = 1; i <= 9; i++) {
            char[] cp = board.clone();
            if (isfree(cp, i)) {
                cp[i] = pletter;
                if (isWinner(cp, pletter)) return i;
            }
        }
        if (isfree(board, 5)) return 5;

        if ((board[5] == cletter) && (board[1] == '\u0000') && board[3] == '\u0000' && board[7] == '\u0000' && board[9] == '\u0000') {
            int mv = chooseRandom(board, p);
            if (mv != -1) return mv;
            return chooseRandom(board, q);
        }
        if (board[5] == cletter) {
            int mv = chooseRandom(board, q);
            if (mv != -1) return mv;
            return chooseRandom(board, p);
        } else {
            int mv = chooseRandom(board, p);
            if (mv != -1) return mv;
            return chooseRandom(board, q);
        }
    }

    public boolean isFull(char[] board) {
        for (int i = 1; i <= 9; i++) if (isfree(board, i)) return false;
        return true;
    }

    public void compMove() {
        int tapped = getCompMove(board, 'X');
        ImageView counter = (ImageView) findViewById(R.id.grid).findViewWithTag("" + tapped);
        board[tapped] = 'X';
        counter.setTranslationX(-1000f);
        counter.setImageResource(R.drawable.cross);
        counter.animate().translationXBy(1000f).setDuration(400);
        if (isWinner(board, 'X')) {
            TextView winner = (TextView) findViewById(R.id.winner);
            winner.setText("Comp Wins !!");
            gameplay = false;
            LinearLayout lay = (LinearLayout) findViewById(R.id.linear);
            lay.setVisibility(View.VISIBLE);
        } else {
            if (isFull(board)) {
                TextView winner = (TextView) findViewById(R.id.winner);
                winner.setText("its a tie");
                gameplay = false;
                LinearLayout lay = (LinearLayout) findViewById(R.id.linear);
                lay.setVisibility(View.VISIBLE);
            }
        }
    }

    public void dropin(View view) {
        ImageView counter = (ImageView) view;
        int tapped = Integer.parseInt(counter.getTag().toString());

        if (board[tapped] == '\u0000' && gameplay) {
            board[tapped] = 'O';
            counter.setTranslationY(-1000f);
            counter.setImageResource(R.drawable.circle);
            counter.animate().translationYBy(1000f).setDuration(400);
            if (isWinner(board, 'O')) {
                TextView winner = (TextView) findViewById(R.id.winner);
                winner.setText("U defeated comp !!");
                gameplay = false;
                LinearLayout lay = (LinearLayout) findViewById(R.id.linear);
                lay.setVisibility(View.VISIBLE);
            } else {
                if (isFull(board)) {
                    TextView winner = (TextView) findViewById(R.id.winner);
                    winner.setText("its a tie");
                    gameplay = false;
                    LinearLayout lay = (LinearLayout) findViewById(R.id.linear);
                    lay.setVisibility(View.VISIBLE);
                } else compMove();
            }
        }
    }

    public void playagain(View view) {
        TextView winner = (TextView) findViewById(R.id.winner);
        winner.setText("");
        LinearLayout lay = (LinearLayout) findViewById(R.id.linear);
        lay.setVisibility(View.INVISIBLE);
        board = new char[10];
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
        gameplay = true;
        activeplayer = whofirst();
        if (activeplayer == 'X') compMove();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeplayer = whofirst();
        gameplay = true;
        board = new char[10];
        if (activeplayer == 'X') compMove();
    }
}
