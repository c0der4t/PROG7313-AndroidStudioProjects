package vcmsa.projects.grannyapp2025

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.log

var SignedIN: Int = -1
var arrUser = ArrayList<User>()
val logTag : String = "monte"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var Username: EditText = findViewById(R.id.txtName)
        var Password: EditText = findViewById(R.id.txtPassword)

        arrUser.add(User("Granny", "1234", "https://picsum.photos/200/300"))
        arrUser.add(User("Grandpa", "4321", "https://picsum.photos/200/300"))
        arrUser.add(User("Timmy", "6969", "https://picsum.photos/200/300"))
        arrUser.add(User("John", "6565", "https://picsum.photos/200/300"))

        Log.d(logTag, "Loaded user list into array DB")

        var btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener() {

            var found = false
            SignedIN = -1

            for (i in 0..arrUser.size-1) {

                if (
                    Username.text.toString().equals(arrUser[i].Name)
                    && Password.text.toString().equals(arrUser[i].Password)
                ) {
                    Toast.makeText(
                        this,
                        "Name: " + Username.text + "\nPassword: " + Password.text,
                        Toast.LENGTH_LONG
                    ).show()
                    found = true
                    SignedIN = i
                    Log.d(logTag,"User logged in\t" + i + "\t|\t" + Username.text)

                    //Intent is how we move to a new form
                    //This is similar to instantiating the form in C#
                    //This is similar to showdialog. The src screen is still running
                    val intent = Intent(this,welcome::class.java)
                    startActivity(intent)

                    break
                }
            }

            //random change

            if (found == false){
                Toast.makeText(this,"RUNNNNNNNNN....",Toast.LENGTH_LONG).show()
                Log.d(logTag,"Failed login attempt\t" + Username.text)
            }


        }


    }
}