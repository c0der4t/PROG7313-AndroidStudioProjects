package com.example.fragmentsdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private val Frag1 = FirstFragment()
private val Frag2 = SecondFragment()

class MenuBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Default to the first screen on load
        replaceFrag(Frag1)

        // We hook into the navbar and then wait for the item selected
        // listener to trigger.
        val bottomBar = findViewById<BottomNavigationView>(R.id.NavBar)
        bottomBar.setOnItemSelectedListener {
            //Once the listener triggers, we do a switch
            // to find out which items was selected and then call the method for that screen
            when (it.itemId) {
                R.id.ic_home -> replaceFrag(Frag1)

                R.id.ic_settings -> replaceFrag(Frag2)
            }
            true
        }
    }

    // This takes in a fragment class / object. Note that it
    // does not take in the layout / xml file but instead the .kt object file
    private fun replaceFrag(fragment: Fragment) {
        if (fragment != null) {

            // The use of the fragment manager / transaction is just boilerplate
            // and will be consistent
            val transaction = supportFragmentManager.beginTransaction()
           // Note that we hook into the framelayout UI element in activity_menu_bar
           // and we then tell it to load the relevant fragment
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        }
    }
}
