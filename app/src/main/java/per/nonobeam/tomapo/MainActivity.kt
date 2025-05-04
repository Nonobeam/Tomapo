package per.nonobeam.tomapo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import per.nonobeam.tomapo.ui.theme.TomapoTheme
import per.nonobeam.tomapo.app.tomapo.TomatoMainScreen
import per.nonobeam.tomapo.app.todo.TodoMainScreen
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import per.nonobeam.tomapo.app.account.AccountMainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomapoApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TomapoApp() {
    val tabs = listOf("Todo", "Tomapo", "Account")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TomapoTheme {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = { Text("Tomapo") }
                    )
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title) }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            when (selectedTabIndex) {
                0 -> TodoMainScreen(Modifier.padding(innerPadding))
                1 -> TomatoMainScreen(Modifier.padding(innerPadding))
                2 -> AccountMainScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

