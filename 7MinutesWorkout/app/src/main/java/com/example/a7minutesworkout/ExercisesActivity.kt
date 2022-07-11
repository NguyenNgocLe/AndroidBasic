package com.example.a7minutesworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityExercisesBinding

class ExercisesActivity: AppCompatActivity() {
    // create a binding variable
    private var binding: ActivityExercisesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // todo 2: Inflate the layout
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        // todo 3: pass in binding?.root content view
        setContentView(binding?.root)
        // todo 4: then set support action bar and get toolBarExercises using the binding
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            binding?.toolbarExercise?.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}