package com.opticod.scarnedice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final Handler timerHandler = new Handler();
    long userOverallScore, userTurnScore, computerOverallScore, computerTurnScore;
    boolean nextStepComputer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;

        final TextView userScoreTextView = findViewById(R.id.textView);

        Button rollButton = findViewById(R.id.button_roll);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int randDiceVal = rand.nextInt(6) + 1;

                userTurnScore += randDiceVal;
                if (randDiceVal != 1) {
                    userScoreTextView.setText(String.format("Your score: %d Computer score: %d Your turn score: %d", userOverallScore, computerOverallScore, userTurnScore));
                } else {
                    userTurnScore = 0;
                    userScoreTextView.setText(String.format("Your score: %d Computer score: %d You rolled a one", userOverallScore, computerOverallScore));
                    computerTurnWithDelay();
                }


                ImageView diceImage = findViewById(R.id.imageView);

                switch (randDiceVal) {
                    case 1:
                        diceImage.setImageResource(R.drawable.dice1);
                        break;
                    case 2:
                        diceImage.setImageResource(R.drawable.dice2);
                        break;
                    case 3:
                        diceImage.setImageResource(R.drawable.dice3);
                        break;
                    case 4:
                        diceImage.setImageResource(R.drawable.dice4);
                        break;
                    case 5:
                        diceImage.setImageResource(R.drawable.dice5);
                        break;
                    case 6:
                        diceImage.setImageResource(R.drawable.dice6);
                        break;
                }
            }
        });

        final Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOverallScore = 0;
                userTurnScore = 0;
                computerOverallScore = 0;
                computerTurnScore = 0;

                userScoreTextView.setText(String.format("Your score: %d Computer score: %d", userOverallScore, computerOverallScore));
            }
        });

        final Button holdButton = findViewById(R.id.button_hold);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOverallScore += userTurnScore;
                userTurnScore = 0;
                userScoreTextView.setText(String.format("Your score: %d Computer score: %d", userOverallScore, computerOverallScore));

                computerTurnWithDelay();
            }
        });
    }

    public void computerTurn() {
        final Button resetButton = findViewById(R.id.button_reset);
        final Button holdButton = findViewById(R.id.button_hold);
        final TextView computerScoreTextView = findViewById(R.id.textView);

        resetButton.setEnabled(false);
        holdButton.setEnabled(false);


        Random rand = new Random();
        int randDiceVal = rand.nextInt(6) + 1;

        ImageView diceImage = findViewById(R.id.imageView);

        switch (randDiceVal) {
            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                break;
        }

        computerTurnScore += randDiceVal;
        if (randDiceVal != 1) {
            computerScoreTextView.setText(String.format("Your score: %d Computer score: %d Computer holds: %d", userOverallScore, computerOverallScore, computerTurnScore));
        } else {
            computerTurnScore = 0;
            computerScoreTextView.setText(String.format("Your score: %d Computer score: %d Computer rolled a one", userOverallScore, computerOverallScore));
            nextStepComputer = false;
        }

        if (computerTurnScore >= 20) {
            computerOverallScore += computerTurnScore;
            computerTurnScore = 0;
            nextStepComputer = false;
            computerScoreTextView.setText(String.format("Your score: %d Computer score: %d", userOverallScore, computerOverallScore));
        }

        resetButton.setEnabled(true);
        holdButton.setEnabled(true);

    }

    private void computerTurnWithDelay() {
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextStepComputer = true;
                computerTurn();
                if (nextStepComputer) {
                    computerTurnWithDelay();
                }
            }
        }, 2000);
    }
}
