package nz.adjmunro.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.adjmunro.calorytracker.navigation.navigate
import nz.adjmunro.calorytracker.ui.theme.CaloryTrackerTheme
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.onboarding.presentation.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                   startDestination = Routes.WELCOME,
                ) {
                    composable(Routes.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(Routes.AGE) {

                    }
                    composable(Routes.GENDER) {

                    }
                    composable(Routes.HEIGHT) {

                    }
                    composable(Routes.WEIGHT) {

                    }
                    composable(Routes.NUTRIENT_GOAL) {

                    }
                    composable(Routes.ACTIVITY) {

                    }
                    composable(Routes.GOAL) {

                    }
                    composable(Routes.TRACKER_OVERVIEW) {

                    }
                    composable(Routes.SEARCH) {

                    }
                }
            }
        }
    }
}
