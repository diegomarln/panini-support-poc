package cr.ac.una.paninisupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cr.ac.una.paninisupport.ui.navigation.AppNavGraph
import cr.ac.una.paninisupport.ui.theme.PaniniSupportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaniniSupportTheme {
                AppNavGraph()
            }
        }
    }
}
