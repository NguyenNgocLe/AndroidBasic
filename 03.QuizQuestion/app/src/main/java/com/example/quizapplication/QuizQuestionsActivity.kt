package com.example.quizapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapplication.Model.Constants
import com.example.quizapplication.Model.Question
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var tvImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_quiz_questions)
        setupViews()
        defaultOptionsView()
        setQuestion()
    }

    private fun setupViews() {

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        tvImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        mQuestionList = Constants.getQuestion()
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        if (mCurrentPosition == mQuestionList?.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        // Get List question
        val question: Question =
            mQuestionList!![mCurrentPosition - 1]
        Log.e("Questions Size", "${mQuestionList!!.size}")
        defaultOptionsView()
        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
        progressBar?.progress = mCurrentPosition
        tvQuestion?.text = question.question
        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
        // set current question and options in the UI
        tvQuestion?.text = question.question
        tvImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }
        for (item in options) {
            item.setTextColor(Color.parseColor("#FF0000"))
            item.typeface = Typeface.DEFAULT
            item.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun setOptionView(textView: TextView?, position: Int) {
        defaultOptionsView()
        mSelectOptionPosition = position
        textView?.setTextColor(Color.parseColor("#363A43"))
        textView?.setTypeface(textView.typeface, Typeface.BOLD)
        textView?.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.select_draw_color
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                setOptionView(tvOptionOne, 1)
            }

            R.id.tv_option_two -> {
                setOptionView(tvOptionTwo, 2)
            }

            R.id.tv_option_three -> {
                setOptionView(tvOptionThree, 3)
            }

            R.id.tv_option_four -> {
                setOptionView(tvOptionFour, 4)
            }

            R.id.btn_submit -> {
                handleSubmit()
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
        }
    }

    private fun handleSubmit() {
        if (mSelectOptionPosition == 0) {
            mCurrentPosition++
            when {
                mCurrentPosition <= mQuestionList!!.size -> {
                    setQuestion()
                }
                else -> {
                    Toast.makeText(
                        this@QuizQuestionsActivity,
                        "You have successfully completed the quiz.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            val question = mQuestionList?.get(mCurrentPosition - 1)
            // check answer wrong
            if (question!!.correctAnswer != mSelectOptionPosition) {
                answerView(mSelectOptionPosition, R.drawable.wrong_answer_border_bg)
            }
            answerView(question.correctAnswer, R.drawable.correct_answer_border_bg)
            if (mCurrentPosition == mQuestionList!!.size) {
                btnSubmit?.text = "FINISH"
            } else {
                btnSubmit?.text = "GO TO NEXT QUESTION"
            }
            mSelectOptionPosition = 0
        }
    }
}