package vcmsa.projects.guessthenumber

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var randomNumber : Int = 5

    fun diceRoll(){

        randomNumber = Random.nextInt(1,101)
        Log.d(TAG,"Random Number $randomNumber")
        Toast.makeText(this,"A new number has been generated",Toast.LENGTH_LONG).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val submitButton : Button = findViewById(R.id.btnSubmit)
        val regenButton : FloatingActionButton = findViewById(R.id.btnRegenerate)
        val txtGuess : EditText = findViewById(R.id.edtGuess)
        val tvOutput : TextView = findViewById(R.id.lblOutput)
        val imgThing : ImageView = findViewById(R.id.imgMainImage)


        diceRoll()
        tvOutput.text = "Guess the Number!"

        submitButton.setOnClickListener {

            var guess : Int? = txtGuess.text.toString().toIntOrNull();

            if(guess != null){
                when{

                    guess > randomNumber -> {
                        tvOutput.text = "Too High"
                        Log.d(TAG, "Guess too high!")
                    }

                    guess < randomNumber -> {
                        tvOutput.text = "Too Low"
                        Log.d(
                                TAG,
                        "Guess too low"
                        )
                    }

                    else -> {
                        tvOutput.text = "Correct, the number IS $randomNumber"
                        Log.d(TAG,
                            "Number guessed correctly")
                        imgThing.setImageResource(R.drawable.success)
                    }

                }
            }else{
                Toast.makeText(this, "Please enter a number as the guess", Toast.LENGTH_LONG).show()
            }
        }

        regenButton.setOnClickListener{
            imgThing.setImageResource(R.drawable.images)
            diceRoll()
            tvOutput.text = "Guess the Number!"
        }

    }
}