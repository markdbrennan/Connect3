package com.mark_brennan.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red
    int activePlayer = 0;
    boolean gameActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Winning position combinations
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        // Get the image tag
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // Set image resource if ImageView tag is 2 (unplayed square)
        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate()
                    .translationYBy(1000f)
                    .rotation(360)
                    .setDuration(400);

            // Check if a player has won
            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    gameActive = false;

                    // A player has won
                    TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);

                    // Check which player has won
                    if (gameState[winningPosition[0]] == 0) {
                        winnerMessage.setText("Yellow has won!");
                    } else {
                        winnerMessage.setText("Red has won!");
                    }

                    // Display message and play again button
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                } else {
                    // If a draw occurs

                    boolean gameOver = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameOver = false;
                        }
                    }

                    if (gameOver) {
                        // Display message and play again button
                        TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It was a draw!");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }

                }
            }
        }


    }

    public void playAgain(View view) {

        gameActive = true;

        // Hide winner message
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        // Reset active player
        activePlayer = 0;

        // Reset game state
        for(int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // Reset images to have no src
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
