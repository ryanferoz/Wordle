package com.example.wordle

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible


class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
    private var maxGuesses = 3
    private var numGuesses = maxGuesses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var simpleEditText = findViewById<View>(R.id.input) as EditText
        var strValue = simpleEditText.text.toString()
        var correct = findViewById<TextView>(R.id.correct)
        var submitButton = findViewById<Button>(R.id.submit_button)
        var resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setBackgroundColor(Color.GRAY)
        var guesses = findViewById<TextView>(R.id.guesses)


        submitButton.setOnClickListener {
            if(numGuesses > 0) {
                simpleEditText = findViewById<View>(R.id.input) as EditText
                strValue = simpleEditText.text.toString().uppercase()
                if (strValue.length == 4) {
                    guesses.text = "" + guesses.text + strValue + " " + checkGuess(strValue) + " "
                    numGuesses--
                    simpleEditText.text.clear()
                    hideSoftKeyboard(simpleEditText)
                } else {
                    Toast.makeText(it.context, "Guess must be 4 letters long.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if(numGuesses <= 0 || wordToGuess.equals(strValue)){
                submitButton.isInvisible = true
                resetButton.isInvisible = false
                correct.text = "" + correct.text + wordToGuess
            }
        }

        resetButton.setOnClickListener {
            guesses.text = ""
            numGuesses = maxGuesses
            submitButton.isInvisible = false
            resetButton.isInvisible = true
            wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
            correct.text = "Correct Word: "
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}