package nz.adjmunro.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import nz.adjmunro.calorietracker.navigation.Routes
import nz.adjmunro.calorietracker.ui.theme.CalorieTrackerTheme
import nz.adjmunro.core.domain.preferences.Preferences
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()

        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = when (shouldShowOnboarding) {
                            true -> Routes.WELCOME
                            else -> Routes.TRACKER_OVERVIEW
                        },
                        modifier = Modifier.padding(it),
                    ) {
                        composable(Routes.WELCOME) {
                            WelcomeScreen(onNextClicked = {
                                navController.navigate(Routes.GENDER)
                            })
                        }
                        composable(Routes.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClicked = {
                                    navController.navigate(Routes.HEIGHT)
                                },
                            )
                        }
                        composable(Routes.GENDER) {
                            GenderScreen(onNextClicked = {
                                navController.navigate(Routes.AGE)
                            })
                        }
                        composable(Routes.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClicked = {
                                    navController.navigate(Routes.WEIGHT)
                                },
                            )
                        }
                        composable(Routes.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = {
                                    navController.navigate(Routes.ACTIVITY)
                                },
                            )
                        }
                        composable(Routes.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClicked = {
                                    navController.navigate(Routes.GOAL)
                                },
                            )
                        }
                        composable(Routes.ACTIVITY) {
                            ActivityLevelScreen(onNextClicked = {
                                navController.navigate(Routes.NUTRIENT_GOAL)
                            })
                        }
                        composable(Routes.GOAL) {
                            GoalTypeScreen(onNextClicked = {
                                navController.navigate(Routes.TRACKER_OVERVIEW)
                            })
                        }
                        composable(Routes.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { meal, day, month, year ->
                                val route = buildString {
                                    append(Routes.SEARCH)
                                    append("/", meal.name.asString(context = this@MainActivity))
                                    append("/", meal.mealType.id)
                                    append("/", day)
                                    append("/", month)
                                    append("/", year)
                                }
                                navController.navigate(route = route)
                            })
                        }
                        composable(
                            route = Routes.SEARCH + "/{mealName}/{mealType}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") { type = NavType.StringType },
                                navArgument("mealType") { type = NavType.StringType },
                                navArgument("dayOfMonth") { type = NavType.IntType },
                                navArgument("month") { type = NavType.IntType },
                                navArgument("year") { type = NavType.IntType },
                            ),
                        ) { nav ->

                            val mealName = nav.arguments?.getString("mealName")!!
                            val mealType = nav.arguments?.getString("mealType")!!
                            val dayOfMonth = nav.arguments?.getInt("dayOfMonth")!!
                            val month = nav.arguments?.getInt("month")!!
                            val year = nav.arguments?.getInt("year")!!

                            SearchScreen(
                                mealName = mealName,
                                mealType = mealType,
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
