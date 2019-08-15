package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.model.Question;
import com.example.trivia.data.QuestionBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //list of questions pulled from trivia api
    private List<Question> questionList;
    private TextView countTextview;
    private TextView questionTextview;
    private TextView scoreTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private static final String MESSAGE_ID = "messages_prefs" ;


    private int questionIndex = 0;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countTextview = findViewById(R.id.question_count);
        questionTextview = findViewById(R.id.question_content);
        scoreTextView = findViewById(R.id.score);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);

        questionIndex = getSharedPrefs.getInt("currIndex", 0);
        score = getSharedPrefs.getInt("score", 0);

        scoreTextView.setText("Score: " + score);
        countTextview.setText(questionIndex + "/" + 913);


        //make sure array is initialized after we are done pulling from api
        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinish(ArrayList<Question> questionArrayList) {
                //questionTextview.setText(questionList.get(0).getQuestion());
                questionTextview.setText(questionList.get(questionIndex).getQuestion());




                //Log.d("Inside", "processDone: " + questionArrayList);


            }
        });





    }

    private void changeQuestion(int questionIndex) {
        if (questionIndex < 0) {
            questionIndex = 0;
        }
        if (questionIndex > 912) {
            questionIndex = 912;
        }

        //String message = enterMessage.getText().toString();
        //update score and current question according to last saved user data
        SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("score", score);
        editor.putInt("currIndex", questionIndex);
        editor.apply();

        questionTextview.setText(questionList.get(questionIndex).getQuestion());
        scoreTextView.setText("Score: " + score);
        countTextview.setText(questionIndex + "/" + 913);



        //Log.d("index", String.valueOf(questionList.size()));

    }
    //check if user answered correctly
    private void checkAnswer(boolean answer) {
        int toastMessageId;
        if (answer == questionList.get(questionIndex).getAnswerTrue()) {
            toastMessageId = R.string.correct;
            score += 100;
            fadeView();
        }
        else {
            toastMessageId = R.string.incorrect;
            score -= 100;
            shakeAnimation();
        }
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                questionIndex++;
                changeQuestion(questionIndex);
                break;
            case R.id.prev_button:
                questionIndex--;
                changeQuestion(questionIndex);
                break;
            case R.id.false_button:
                checkAnswer(false);
                changeQuestion(questionIndex);

                break;
            case R.id.true_button:
                checkAnswer(true);
                changeQuestion(questionIndex);

                break;
        }
    }
    //incorrect shaking animation
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    //correct flashing animation
    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
