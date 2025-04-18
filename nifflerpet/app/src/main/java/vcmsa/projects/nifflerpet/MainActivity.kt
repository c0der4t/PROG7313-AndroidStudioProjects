package vcmsa.projects.nifflerpet

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var statHappiness = 100
    var statHealth = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val pbarHappiness = findViewById<ProgressBar>(R.id.pbarHappiness)
        val pbarHealth= findViewById<ProgressBar>(R.id.pbarHealth)

        pbarHappiness.progress = statHappiness
        pbarHealth.progress = statHealth

        navBar.selectedItemId= -1;
        switchState(IdleFragment())



        navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuItem_Wash -> switchState(AttentionFragment())
                R.id.menuitem_GIveTrinket -> switchState(TrinketFragment())
                R.id.menuItem_EmptyPouch -> switchState(CleaningFragment())
                R.id.menuItem_Sleep -> switchState(WasteFragment())
            }
            true
        }
    }

    private fun switchState(stateFragment: Fragment){
        if(stateFragment != null){
            val trans = supportFragmentManager.beginTransaction()

            trans.replace(R.id.frameLayout,stateFragment)
            trans.commit()
        }
    }
}