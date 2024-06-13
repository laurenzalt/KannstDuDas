package at.fhj.kannstdudas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import at.fhj.kannstdudas.navigation.AuthNavGraph
import at.fhj.kannstdudas.navigation.MainNavGraph
import at.fhj.kannstdudas.presentation.theme.KannstDuDasTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            KannstDuDasTheme {
                MainNavGraph(navController = rememberNavController())
                //AuthNavGraph(navController = rememberNavController())
            }
        }
    }
}