package com.example.quizapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapplication.Model.Constants

class QuizQuestionsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_quiz_questions)
        // Get List question
        val questionsList = Constants.getQuestion()
        Log.e("Questions Size", "${questionsList.size}")
        for (i in questionsList) {
            Log.e("Questions", i.question)
        }
        // END
    }
}