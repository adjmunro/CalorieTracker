package nz.adjmunro.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import nz.adjmunro.calorietracker.navigation.navigate
import nz.adjmunro.calorietracker.ui.theme.CaloryTrackerTheme
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.onboarding.presentation.activity.ActivityLevelScreen
import nz.adjmunro.onboarding.presentation.age.AgeScreen
import nz.adjmunro.onboarding.presentation.gender.GenderScreen
import nz.adjmunro.onboarding.presentation.goal.GoalTypeScreen
import nz.adjmunro.onboarding.presentation.height.HeightScreen
import nz.adjmunro.onboarding.presentation.nutrient.NutrientGoalScreen
import nz.adjmunro.onboarding.presentation.weight.WeightScreen
import nz.adjmunro.onboarding.presentation.welcome.WelcomeScreen
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewScreen
import nz.adjmunro.tracker.presentation.search.SearchScreen

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
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(Routes.ACTIVITY) {
                            ActivityLevelScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.GOAL) {
                            GoalTypeScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = Routes.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") { type = NavType.StringType },
                                navArgument("dayOfMonth") { type = NavType.IntType },
                                navArgument("month") { type = NavType.IntType },
                                navArgument("year") { type = NavType.IntType },
                            ),
                        ) { nav ->

                            val mealName = nav.arguments?.getString("mealName")!!
                            val dayOfMonth = nav.arguments?.getInt("dayOfMonth")!!
                            val month = nav.arguments?.getInt("month")!!
                            val year = nav.arguments?.getInt("year")!!

                            SearchScreen(
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                scaffoldState = scaffoldState,
                                onNavigateUp = navController::navigateUp,
                            )
                        }
                    }
                }
            }
        }
    }
}
