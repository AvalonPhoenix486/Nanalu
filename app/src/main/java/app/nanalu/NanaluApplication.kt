package app.nanalu

import android.app.Application

class NanaluApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}
