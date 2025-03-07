package vcmsa.projects.grannyapp2025

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class welcome : AppCompatActivity() {

    lateinit var userAdapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var tvOutput : TextView = findViewById(R.id.tvWelcome)
        tvOutput.setText("Welcome\n" + arrUser[SignedIN].Name.toString())


        var image:Bitmap? = null
        val imOutput : ImageView = findViewById(R.id.imwelcome_welcome)

        // Next we will be utilizing a thread SEPARATE from the thread we are on

        // Handler is a reference that we save which
        // points to the current thread we are on
        // We set this up so we can reach back out to the current thread
        // when we jump into a different thread
        // This is important, since threads cannot talk to each other directly,
        // It needs to use a 'tunnel' or conduit to talk to each other
        val handler = Handler(Looper.getMainLooper())

        // Similar to a task factory in C#
        // Essentially, the Executors generates the thread 'object' for us which
        // we will then interact with. This MUST be done because the new thread must be declared
        // to the OS and loaded onto the CPU to get CPU time. However, the thread does not do anything
        // just yet.
        val executor = Executors.newSingleThreadExecutor()

        // Here we run the .execute call.
        // Essentially it runs the code in between {} similar to how a method will run
        // You could also assign a separate method instead of using the inline / lambda? method
        // When we call this .execute call, it provides our thread with work to do and it will start doing
        // it during it's CPU cycle
        executor.execute{

            // Notice how the arrUser can still be access in a different thread
            // Because it is a global var and need not be passed between threads like C# will require
            val imageURL = arrUser[SignedIN].imageURL
            try {

                // The in is encased in tilde's because it is a reserved word
                // If you changed it to incomingStream or test you would drop the tilde
                val `in` = java.net.URL(imageURL).openStream()

                // Similar to arrUser, the image var can be passed between threads without needing to be
                // passed in directly
                image = BitmapFactory.decodeStream(`in`)
                Log.d("monte","Image has been added " + image.toString())

                // The post method allows us to jump back into the main thread
                // This essentially allows us to send an interrupt signal to the thread the handle
                // points to, and then inject / run the code in the lambda function
                handler.post{
                    Log.d("monte","Image has been added")
                    imOutput.setImageBitmap(image)
                }

            }
            // Note here that e is the name of the variable
            // the :java.lang.Exception is the TYPE of the variable
            catch (e:java.lang.Exception)
            {
                Log.d("monte","Error during image load: " + e.toString())
                e.printStackTrace()

            }
        }

        val feed : RecyclerView = findViewById(R.id.welcome_RecycleView)

        userAdapter = UserAdapter()

        feed.apply {
            layoutManager = LinearLayoutManager(this@welcome)
            adapter = userAdapter
        }

//        val items = mutableListOf<User>()
//        for (i in 0..40){
//            items.add(
//                User(
//                    Name="Name test $i",
//                    Password = "Password$1",
//                    imageURL = "https://picsum.photos/200/300"
//                )
//            )
//        }
//
//        userAdapter.submitList(items)

       val executor2 = Executors.newSingleThreadExecutor()

        executor2.execute {
            //fetch data from external API into list of objects
            val url = URL("https://prog7313.azurewebsites.net/?userdb")
            val json = url.readText()
            Log.d("monte",json.toString())
            val userList = Gson().fromJson(json, Array<User>::class.java).toList()
            Handler(Looper.getMainLooper()).post {
                userAdapter.submitList(userList)
            }
        }
    }
}