package sample.latest.news.core.util.ui

import android.content.Context
import androidx.activity.ComponentActivity
import sample.latest.news.core.util.localizedContext

abstract class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(localizedContext(context))
    }

    override fun onStart() {
        super.onStart()
        localizedContext(this)
    }
}
