package com.example.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {
    // create a binding variable
    private var binding: ActivityExercisesBinding? = null

    // Timer
    private var restTimer: CountDownTimer? = null
    private var resProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // todo 2: Inflate the layout
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        // todo 3: pass in binding?.root content view
        setContentView(binding?.root)
        // todo 4: then set support action bar and get toolBarExercises using the binding
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            binding?.toolbarExercise?.setNavigationOnClickListener {
                onBackPressed()
            }
        }

        setupRestView()
    }

    private fun setupRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            resProgress = 0
        }
        setProgressBar()
    }

    private fun setProgressBar() {
        binding?.progressBar?.progress = resProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                resProgress++
                binding?.progressBar?.progress = 10 - resProgress
                binding?.tvTimer?.text = (10 - resProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExercisesActivity,
                    "Here now we will start the exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            resProgress = 0
        }
        super.onDestroy()
        binding = null
    }
}