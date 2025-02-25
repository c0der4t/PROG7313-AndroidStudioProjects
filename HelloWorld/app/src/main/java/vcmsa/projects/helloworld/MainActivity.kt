package vcmsa.projects.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //Boiler Plate
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // You have to define a reference to your button
        //This works similar like traversing the DOM in JS to find the object
        val myButton : Button = findViewById(R.id.btnPress)

        //Here we are using a delegate method assigned inline
        //You could also build out the delegate and assign it like a variable
        myButton.setOnClickListener {
            val label : TextView = findViewById(R.id.textView)
            Toast.makeText(this,"Button clicked!",Toast.LENGTH_SHORT).show()
            label.setText("Hello new new world")
        }

        val floatButton : FloatingActionButton = findViewById(R.id.btnFloatingAction)

        floatButton.setOnClickListener {

            val grouping : ChipGroup = findViewById(R.id.chipGroup)
            var chipID = grouping.getCheckedChipId()
            var selectedchip : Chip = findViewById(chipID)
            var searchText = selectedchip.text.toString();

            Toast.makeText(this,"Chip Selected: $searchText",Toast.LENGTH_LONG).show()
        }

    }
}