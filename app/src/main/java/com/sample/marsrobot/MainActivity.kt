package com.sample.marsrobot

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.computeBtn).setOnClickListener {
            val gridX = findViewById<TextInputEditText>(R.id.gridXText).text.toString()
            val gridY = findViewById<TextInputEditText>(R.id.gridYText).text.toString()
            val robotX = findViewById<TextInputEditText>(R.id.robotXText).text.toString()
            val robotY = findViewById<TextInputEditText>(R.id.robotYText).text.toString()
            val robotDirection =
                findViewById<TextInputEditText>(R.id.robotDirectionText).text.toString()
            val instructions =
                findViewById<TextInputEditText>(R.id.instructionsText).text.toString()

            findViewById<TextView>(R.id.result).text = ""

            if (presenter.initGrid(gridX, gridY)) {
                if (presenter.initRobotPosition(robotX, robotY, robotDirection)){
                    displayResult(presenter.computeRobotPosition(instructions))
                } else {
                    displayResult(getString(R.string.invalid_initial_robot_values))
                }
            } else {
                displayResult(getString(R.string.invalid_values_grid))
            }
        }
    }

    private fun displayResult(msg: String) {
        findViewById<TextView>(R.id.result).text = "Result= $msg"
    }
}
