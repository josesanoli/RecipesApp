package es.jolusan.appdemo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.jolusan.appdemo.R
import es.jolusan.appdemo.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}