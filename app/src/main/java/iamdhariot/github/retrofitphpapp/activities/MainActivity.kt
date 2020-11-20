 package iamdhariot.github.retrofitphpapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.fragments.MainFragment

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // loading the default main fragment
        val mainFragment = MainFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_layout,mainFragment, mainFragment.tag)
                //.addToBackStack(mainFragment.tag)
                .commit()
    }
}