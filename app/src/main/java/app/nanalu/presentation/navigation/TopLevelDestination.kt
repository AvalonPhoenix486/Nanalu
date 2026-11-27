package app.nanalu.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import app.nanalu.R

/** As cinco áreas principais, na ordem definida em SCREEN_MAP §3. */
enum class TopLevelDestination(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    HOME("home", R.string.nav_home, Icons.Filled.Home),
    HISTORY("history", R.string.nav_history, Icons.AutoMirrored.Filled.List),
    STATS("stats", R.string.nav_stats, StatsIcon),
    CONTENT("content", R.string.nav_content, Icons.Filled.Info),
    PROFILE("profile", R.string.nav_profile, Icons.Filled.Person),
}

private val StatsIcon: ImageVector = ImageVector.Builder(
    name = "Stats",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).path(fill = SolidColor(Color.Black)) {
    moveTo(4f, 12f); lineTo(8f, 12f); lineTo(8f, 20f); lineTo(4f, 20f); close()
    moveTo(10f, 5f); lineTo(14f, 5f); lineTo(14f, 20f); lineTo(10f, 20f); close()
    moveTo(16f, 9f); lineTo(20f, 9f); lineTo(20f, 20f); lineTo(16f, 20f); close()
}.build()
