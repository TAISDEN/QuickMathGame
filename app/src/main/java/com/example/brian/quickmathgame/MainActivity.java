package com.example.brian.quickmathgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity
    {
    private TextView answerView;
    private TextView probView;
    private TextView probView2;
    private TextView symbolView;
    private TextView scoreView;
    private TextView livesView;

    private int probNumber1;
    private int probNumber2;
    private int score = 0;
    private boolean gameRunning = false;
    private int probType = 1;
    private int lives = 3;

    Timer timer;
    MyTimerTask myTimerTask;
    int secCount = 0, msecCount = 0, msec2Count = 0;
    private TextView timerSec, timerMsec1, timerMsec2;

    //#################################################################################################
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerView = (TextView)
                findViewById(R.id.answerDisplay);
        probView2 = (TextView)
                findViewById(R.id.problemDisplay);
        probView = (TextView)
                findViewById(R.id.problemDisplay2);
        symbolView = (TextView)
                findViewById(R.id.symbolDisplay);
        scoreView = (TextView)
                findViewById(R.id.scoreNum);
        UpdateScore();
        livesView = (TextView)
                findViewById(R.id.livesNum);
        UpdateLives();

        UpdatePlayTimer();

        timerSec = (TextView)
                findViewById(R.id.TdsplySec);
        timerMsec1 = (TextView)
                findViewById(R.id.Tdsplymsec1);
        timerMsec2 = (TextView)
                findViewById(R.id.Tdsplymsec2);
        }

    //#################################################################################################
/* START---
    WHEN THE START BUTTON IS CLICKED
	START THE GAME AND CHANGE THE BUTTON TEXT
*/
    public void OnClickStartButton(View Sbtn)
        {
        //Button button;
        //button = (Button) Sbtn;
        //Button button = (Button) Sbtn;
        //button.setText("RESET GAME");
        if (!gameRunning)
            {
            gameRunning = true;
            score = 0;
            lives = 3;
            UpdateScore();
            UpdateLives();
            ((Button) Sbtn).setText("RESET GAME");
            MakeMathProblem();
            //timer = new Timer();
            //timer.schedule(count, 1000);
            SetCounterTimer(10);
            } else
            {
            if (timer != null)
                {
                timer.cancel();
                timer = null;
                }
            gameRunning = false;
            ((Button) Sbtn).setText("START GAME");
            }
        }

    //#################################################################################################
/* RANDOM PROBLEM---
	MAKE THE RANDOM MATH PROBLEM FOR THE PLAYER
*/
    public void MakeMathProblem()
        {
		/*randomNum = minimum + (int)(Math.random()*maximum);
		Random rn = new Random();
		int n = maximum - minimum + 1;
		int i = rn.nextInt() % n;
		randomNum =  minimum + i;
		*/

        //Random rnd = new Random ();
        //Random rnd2 = new Random ();
        //probNumber1 = rnd.nextInt();
        //int randNumber = GetRandInt(0,10);

        //int value = Integer.parseInt(bText);
        //myTextView.setText(Integer.toString(total))

        probView.setText(null);
        probView2.setText(null);
        symbolView.setText(null);

        //-RANDOMLY CHOOSE THE PROBLEM TYPE
        //- 1 = ADDITION
        //- 2 = SUBTRACTION
        //- 3 = MULTIPLICATION
        probType = GetRandInt(1, 3);

        probNumber1 = GetRandInt(0, 25);
        probNumber2 = GetRandInt(0, 25);

        if (probType == 1)
            {
            probView.setText(Integer.toString(probNumber1));
            symbolView.setText("+");
            }
        //-IF THE PROBLEM TYPE IS SUBTRACTION
        else if (probType == 2)
            {
            //--MAKE IT SO THAT THE MIN FOR THE 1ST NUMBER IS 5
            probNumber1 = GetRandInt(5, 25);
            probView.setText(Integer.toString(probNumber1));
            symbolView.setText("-");

            //--MAKE IT SO THAT THE 2ND NUMBER HAS TO BE LESS THAN THE 1ST NUMBER
            //---NO NEGATIVE ANSWERS
            while (probNumber2 > probNumber1)
                {
                probNumber2 = GetRandInt(0, 24);
                }
            } else if (probType == 3)
            {
            probView.setText(Integer.toString(probNumber1));
            symbolView.setText("x");
            }
        probView2.setText(Integer.toString(probNumber2));
        }

    //#################################################################################################
/* NUMBER ENTERED--- (1-9)
	WHEN ONE OF THE NUMBERS BUTTONS IS PRESSED
	ADD THE NUMBER TO THE ANSWER DISPLAY
*/
    public void OnClickNumberButton(View Nbtn)
        {
        if (gameRunning)
            {
            Button button = (Button) Nbtn;
            String bText = button.getText().toString();
            answerView.append(bText);
            }
        }

    //#################################################################################################
/* BACKSPACE--- (X)
	WHEN THE BACKSPACE IS PUSHED EDIT THE ANSWER BOX
*/
    public void OnClickBackspace(View Bks)
        {
        if (gameRunning)
            {
            String num = answerView.getText().toString();
            int len = num.length();
            if (len <= 1)
                {
                answerView.setText(null);
                } else
                {
                char[] tempCharArray = new char[len - 1];

                //-PUT THE ORIGINAL STRING BACK BUT REMOVE THE LAST CHARACTER
                for (int i = 0; i < len - 1; i++)
                    {
                    tempCharArray[i] = num.charAt(i);
                    }
                String newNum = new String(tempCharArray);
                answerView.setText(newNum);
                }
            }
        }

    //#################################################################################################
/* ANSWER--- (=)
	WHEN THE ANSWER BUTTON IS PRESS SEE IF THE
	PLAYER PUT THE CORRECT ANSWER IN
*/
    public void OnClickAnswerProb(View ansProb)
        {
        if (gameRunning)
            {
            int answer = 0;
            String bText = answerView.getText().toString();
            if (!bText.isEmpty())
                {
                int playerAnswer = Integer.parseInt(bText);
                if (probType == 1) //-ADD
                    {
                    answer = probNumber1 + probNumber2;
                    } else if (probType == 2) //-SUB
                    {
                    answer = probNumber1 - probNumber2;
                    } else if (probType == 3) //-MULTIPLY
                    {
                    answer = probNumber1 * probNumber2;
                    }

                if (playerAnswer == answer)
                    {
                    score++;
                    UpdateScore();
                    MakeMathProblem();
                    } else
                    {
                    lives--;
                    UpdateLives();
                    }
                answerView.setText(null);
                }
            }
        }

    //#################################################################################################
/* RANDOM INTEGER---
	CREATE A RANDOM INT BETWEEN MIN AND MAX NUMBERS.
*/
    public static int GetRandInt(int min, int max)
        {
        int diff = max - min;
        Random rn = new Random();
        int i = rn.nextInt(diff + 1);
        i += min;
        return i;
        }

    //#################################################################################################
/* UPDATE SCORE---
	UPDATE THE SCORE TO THE CURRENT SCORE INT.
 */
    public void UpdateScore()
        {
        scoreView.setText(Integer.toString(score));
        }

    //#################################################################################################
/* UPDATE LIVES---
	UPDATE THE LIVES TO THE CURRENT LIVES INT.
 */
    public void UpdateLives()
        {
        livesView.setText(Integer.toString(lives));
        if (lives == 0)
            {
            gameRunning = false;
            probView.setText("GAME OVER");
            probView2.setText(null);
            symbolView.setText(null);
            Button sBtn = (Button)
                    findViewById(R.id.startButton);
            sBtn.setText("START GAME");
            if (timer != null)
                {
                timer.cancel();
                timer = null;
                }
            }
        }

    //#################################################################################################
/* UPDATE TIMER---
	UPDATE THE TIMER TO THE CURRENT timerCount.
 */
    public void UpdatePlayTimer()
        {
        //timerView.setText(Integer.toString(timerCount));
        }

    //#################################################################################################
    class MyTimerTask extends TimerTask
        {
        @Override
        public void run()
            {
            runOnUiThread(new Runnable()
            {
            @Override
            public void run()
                {
                //timedisplay.setText("Timer Went off...");
                msec2Count++;
                if (msec2Count > 9)
                    {
                    msec2Count = 0;
                    msecCount++;
                    if (msecCount > 9)
                        {
                        msecCount = 0;
                        secCount++;
                        }
                    }
                timerSec.setText(Integer.toString(secCount));
                timerMsec1.setText(Integer.toString(msecCount));
                timerMsec2.setText(Integer.toString(msec2Count));
                //msec2display.setText(Integer.toString(msec2Count));
                //msecdisplay.setText(Integer.toString(msecCount));
                //secdisplay.setText(Integer.toString(secCount));
                }
            });
            }
        }

    //#################################################################################################
    public void SetCounterTimer(int milSecs)
        {
        secCount = 0;
        msecCount = 0;
        msec2Count = 0;

        timerSec.setText(Integer.toString(secCount));
        timerMsec1.setText(Integer.toString(msecCount));
        timerMsec2.setText(Integer.toString(msec2Count));

        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, milSecs, milSecs);
        }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
        {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        }

    //#################################################################################################
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
        {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            {
            return true;
            }

        return super.onOptionsItemSelected(item);
        }
    }