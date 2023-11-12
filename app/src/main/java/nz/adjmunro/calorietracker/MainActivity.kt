package nz.adjmunro.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nz.adjmunro.calorietracker.navigation.navigate
import nz.adjmunro.calorietracker.ui.theme.CaloryTrackerTheme
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.onboarding.presentation.age.AgeScreen
import nz.adjmunro.onboarding.presentation.gender.GenderScreen
import nz.adjmunro.onboarding.presentation.height.HeightScreen
import nz.adjmunro.onboarding.presentation.weight.WeightScreen
import nz.adjmunro.onboarding.presentation.welcome.WelcomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    it
                    NavHost(
                        navController = navController,
                        startDestination = Routes.WELCOME,
                    ) {
                        composable(Routes.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(Routes.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(Routes.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
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
}
