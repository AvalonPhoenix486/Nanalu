package app.nanalu

import android.content.Context
import app.nanalu.data.local.NanaluDatabase
import app.nanalu.data.repository.RoomChildProfileRepository
import app.nanalu.domain.repository.ChildProfileRepository

/** Injeção manual de dependências: suficiente para o MVP (sem framework de DI). */
class AppContainer(context: Context) {
    private val database: NanaluDatabase by lazy { NanaluDatabase.create(context) }

    val childProfileRepository: ChildProfileRepository by lazy {
        RoomChildProfileRepository(database.childProfileDao())
    }
}
