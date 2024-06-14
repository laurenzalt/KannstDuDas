package at.fhj.kannstdudas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import at.fhj.kannstdudas.model.Skill
import at.fhj.kannstdudas.navigation.MainNavGraph
import at.fhj.kannstdudas.presentation.theme.KannstDuDasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val skills = mutableListOf<Skill>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KannstDuDasTheme {
                MainNavGraph(
                    navController = rememberNavController(),
                    addSkill = { skill ->
                        skills.add(0, skill)
                    }
                )
            }
        }
    }
}