 package iamdhariot.github.retrofitphpapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.fragments.MainFragment
import iamdhariot.github.retrofitphpapp.helper.SharedPrefManager

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // if user is already logged in
        // opening the profile activity (i.e NavDrawerActivity)
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this, NavDrawerActivity::class.java))
        }

        // loading the default main fragment
        val mainFragment = MainFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_layout,mainFragment, mainFragment.tag)
                //.addToBackStack(mainFragment.tag)
                .commit()
    }
}