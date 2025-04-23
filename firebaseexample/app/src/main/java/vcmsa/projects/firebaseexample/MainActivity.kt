package vcmsa.projects.firebaseexample

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


// IMPORTANT
/*
* Before you can use this, you need to hook up a reference to your
* firebase instance. Go hamburger > Tools > Firebase
* Here select Real Time Database
* Run through the connect button:
*   You need to create a project. Turn off all monitoring and gemini interventions
*   Then go to Build > Realtime Database
*   Then make one in test mode so it is publicly accessible
*
* Thereafter, in the firebase blade in android studio, click the button to
* add in all of the SDK's you will need into your Gradle
* */

class MainActivity : AppCompatActivity() {

    private lateinit var listview : ListView
    private lateinit var userReference : DatabaseReference
    private  lateinit var rootNode : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        listview = findViewById(R.id.lsOutput)

        // This gets the root of our JSON document
        rootNode = FirebaseDatabase.getInstance()

        // Now we look for the first reference of users in the root of the JSON
        userReference = rootNode.getReference("users")

        val dc = DataClass("b Tapes", "Black Tape", 85, 800)

        // This adds a new json child object to root.users
        // The key of the object is set equal to the id. We can make this anything
        // When this setValue runs, it will create the full JSON path if not exists:
        // root.users.<id>
        userReference.child(dc.id.toString()).setValue(dc)

        val list = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.listitems, list)
        listview.adapter = adapter

        // He we subscribe a custom interface we to the notifier of the
        // values being changes
        userReference.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snapshot1 in snapshot.children) {

                    // Because we have a reference to the root.users
                    // object in the json, this reflection / parse will
                    // work. i.e we will not see other classes
                    val dc2 = snapshot1.getValue(DataClass::class.java)
                    val txt = "Name is ${dc2?.name}, Des: ${dc2?.description}"
                    txt?.let { list.add(it) }
                }

                // This tells our local listview UI object to update
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}