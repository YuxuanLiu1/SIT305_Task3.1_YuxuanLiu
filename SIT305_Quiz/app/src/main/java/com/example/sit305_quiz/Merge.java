package com.example.sit305_quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Merge {
    public static class MainActivity extends AppCompatActivity {
        EditText name;
        Button start;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            name = findViewById(R.id.Name);
            start = findViewById(R.id.startbutton);

            start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(name.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this,"Name is not valid",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,MainActivity2.class);
                        intent.putExtra("name",name.getText().toString());
                        startActivity(intent);


                    }}
            });
        }

    }

    public static class MainActivity2 extends AppCompatActivity {
        TextView topname;
        TextView progressText, questionTitle, questionContent;
        Button button1, button2, button3, Submit;
        ProgressBar progressBar;
        int progress = 1;
        int clicks = 0;
        Boolean record1 = false, record2 = false, record3 = false;
        Boolean nextquestion = false;
        int grade = 0;
        private QuestionLibrary questionLibrary = new QuestionLibrary();
        private int QuestionNumber = 0;
        private int Score = 0;
        private String answer;
        String user;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            topname = findViewById(R.id.topname);
            topname.setText("Welcome "+getIntent().getStringExtra("name"));
            user = getIntent().getStringExtra("name");
            progressBar = findViewById(R.id.progressBar);
            progressText = findViewById(R.id.progressText);
            questionTitle = findViewById(R.id.questionTitle);
            questionContent = findViewById(R.id.questionContent);
            button1 = findViewById(R.id.button1);
            button2 = findViewById(R.id.button2);
            button3 = findViewById(R.id.button3);
            Submit = findViewById(R.id.Submit);
            updateQuestion();

            progressBar.setProgress(progress);
            progressText.setText("1/5");
            Submit.setText("Submit");

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    record1 = true;
                    record2 = false;
                    record3 = false;
                    button1.setBackgroundColor(Color.BLUE);
                    button2.setBackgroundColor(Color.GRAY);
                    button3.setBackgroundColor(Color.GRAY);


                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    record1 = false;
                    record2 = true;
                    record3 = false;
                    button2.setBackgroundColor(Color.BLUE);
                    button1.setBackgroundColor(Color.GRAY);
                    button3.setBackgroundColor(Color.GRAY);


                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    record1 = false;
                    record2 = false;
                    record3 = true;
                    button3.setBackgroundColor(Color.BLUE);
                    button1.setBackgroundColor(Color.GRAY);
                    button2.setBackgroundColor(Color.GRAY);

                }
            });

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicks++;
                    if (nextquestion == false) {
                        Submit.setText("Next");

                        if (record1 == true) {
                            if (button1.getText() == answer) {
                                button1.setBackgroundColor(Color.GREEN);
                                Score = Score + 1;

                            } else {
                                button1.setBackgroundColor(Color.RED);
                                if (button2.getText() == answer) {
                                    button2.setBackgroundColor(Color.GREEN);
                                } else if (button3.getText() == answer)
                                    button3.setBackgroundColor(Color.GREEN);
                            }
                        }

                        if (record2 == true) {
                            if (button2.getText() == answer) {
                                button2.setBackgroundColor(Color.GREEN);
                                Score = Score + 1;

                            } else {
                                button2.setBackgroundColor(Color.RED);
                                if (button1.getText() == answer) {
                                    button1.setBackgroundColor(Color.GREEN);
                                } else if (button3.getText() == answer)
                                    button3.setBackgroundColor(Color.GREEN);
                            }
                        }

                        if (record3 == true) {
                            if (button3.getText() == answer) {
                                button3.setBackgroundColor(Color.GREEN);
                                Score = Score + 1;

                            } else {
                                button3.setBackgroundColor(Color.RED);
                                if (button1.getText() == answer) {
                                    button1.setBackgroundColor(Color.GREEN);
                                } else if (button2.getText() == answer)
                                    button2.setBackgroundColor(Color.GREEN);
                            }
                        }
                        if (grade == 1) {
                            nextquestion = true;
                        }
                        grade++;
                    }

                    if (nextquestion == true) {
                        progressText.setText(progress + "/5");
                        progressBar.setProgress(progress);
                        Submit.setText("Submit");
                        updateQuestion();
                        nextquestion = false;
                        grade = 0;
                        record1 = false;
                        record2 = false;
                        record3 = false;
                    }

                    if (clicks == 9) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity2.this, Feedback.class);
                        intent.putExtra("name", user);
                        intent.putExtra("Score", Score);
                        startActivity(intent);

                    }
                }



            });

        }

        private void updateQuestion() {
            questionTitle.setText(questionLibrary.getTitle(QuestionNumber));
            questionContent.setText(questionLibrary.getQuestion(QuestionNumber));
            button1.setText(questionLibrary.getSelections0(QuestionNumber));
            button2.setText(questionLibrary.getSelections1(QuestionNumber));
            button3.setText(questionLibrary.getSelections2(QuestionNumber));
            answer = questionLibrary.getCorrectAnswer(QuestionNumber);
            button1.setBackgroundColor(Color.GRAY);
            button2.setBackgroundColor(Color.GRAY);
            button3.setBackgroundColor(Color.GRAY);
            QuestionNumber++;
            progress++;

        }
    }

    public static class Feedback extends AppCompatActivity {
        TextView congrats, score;
        Button finish, newquiz;
        private int I;
        int score1;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feedback);
            congrats = findViewById(R.id.textView4);
            score = findViewById(R.id.Score);
            finish = findViewById(R.id.Finish);
            newquiz = findViewById(R.id.New);
            congrats.setText("Congrats " + getIntent().getStringExtra("name"));
            score1 = getIntent().getIntExtra("Score",I);
            String ss = String.valueOf(score1 + "/5");
            score.setText(ss);

            newquiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Feedback.this, MainActivity2.class);
                    String user = getIntent().getStringExtra("name");
                    intent.putExtra("name", user);
                    startActivity((intent));
                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }
            });
        }
    }

    public static class QuestionLibrary {

        private  String Title [] = {
                "Question1",
                "Question2",
                "Question3",
                "Question4",
                "Question5",
        };

        private String Question [] = {
                "What objects are used to build Android layout?",
                "Android is financed by",
                "What is parent class of activity?",
                "Which screen sizes are Android using?",
                "What Layouts can Android use?",
        };

        private String Selections [][] = {
                {"View","ViewGroup","All above"},
                {"Google","Microsoft","Apple"},
                {"Object","ContextThemeWrapper","ActivityGroup"},
                {"Small","Normal","All above"},
                {"Linear Layout","Frame Layout","All above"},

        };

        private String CorrectSelection [] = {
                "All above","Google","ContextThemeWrapper","All above","All above"
        };

        public String getQuestion(int a) {
            String question = Question[a];
            return question;
        }

        public String getTitle(int a) {
            String title = Title[a];
            return  title;
        }

        public String getSelections0(int a) {
            String selection0 = Selections[a][0];
            return selection0;
        }

        public String getSelections1(int a) {
            String selection1 = Selections[a][1];
            return selection1;
        }

        public String getSelections2(int a) {
            String selection2 = Selections[a][2];
            return selection2;
        }

        public String getCorrectAnswer(int a) {
            String answer = CorrectSelection[a];
            return answer;
        }


    }
}
