package at.fhj.kannstdudas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.navigation.AppNavGraph
import at.fhj.kannstdudas.presentation.theme.KannstDuDasTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * at.fhj.kannstdudas
 * Created by Noah Dimmer on 12/06/2024
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val skills = mutableListOf<Skill>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightTheme {
                AppNavGraph(
                    navController = rememberNavController(),
                    navigationViewModel = hiltViewModel(),
                    authViewModel = hiltViewModel()
                )
            }
        }
    }
}

@Composable
fun LightTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Color.White,
            onBackground = Color.Black,
            onPrimary = Color.White,
            primary = Color(0xff046dd6)
        )
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}