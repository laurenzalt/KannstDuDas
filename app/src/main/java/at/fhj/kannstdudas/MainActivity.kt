package at.fhj.kannstdudas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.navigation.RootNavGraph
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
            KannstDuDasTheme {
                RootNavGraph(
                    navController = rememberNavController(),
                    viewModel = hiltViewModel())
            }
        }
    }
}