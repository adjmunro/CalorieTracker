package nz.adjmunro.calorietracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import nz.adjmunro.calorietracker.navigation.Routes
import nz.adjmunro.calorietracker.repository.TrackerRepositoryFake
import nz.adjmunro.calorietracker.ui.theme.CalorieTrackerTheme
import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.models.UserInfo
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import nz.adjmunro.tracker.domain.model.TrackableFood
import nz.adjmunro.tracker.domain.usecase.CalculateMealNutrients
import nz.adjmunro.tracker.domain.usecase.DeleteTrackedFood
import nz.adjmunro.tracker.domain.usecase.GetFoodsForDate
import nz.adjmunro.tracker.domain.usecase.SearchFood
import nz.adjmunro.tracker.domain.usecase.TrackFood
import nz.adjmunro.tracker.domain.usecase.TrackerUseCases
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewScreen
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewViewModel
import nz.adjmunro.tracker.presentation.search.SearchScreen
import nz.adjmunro.tracker.presentation.search.SearchViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@HiltAndroidTest
internal class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repository: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var navController: NavController

    @Before
    fun setUp() {
        // Hilt inject unnecessary because Composables inject ViewModels
        // hiltRule.inject()

        preferences = mockk(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.MediumActivity,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f,
        )

        repository = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences),
        )
        trackerViewModel = TrackerOverviewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases,
        )
        searchViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterOnlyDigits = FilterOnlyDigits(),
        )

        composeRule.setContent {
            CalorieTrackerTheme {

                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.TRACKER_OVERVIEW,
                        modifier = Modifier.padding(it),
                    ) {
                        composable(Routes.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                viewModel = trackerViewModel,
                                onNavigateToSearch = { meal, day, month, year ->
                                    val route = buildString {
                                        append(Routes.SEARCH)
                                        append("/", meal.name.asString(context = context))
                                        append("/", meal.mealType.id)
                                        append("/", day)
                                        append("/", month)
                                        append("/", year)
                                    }
                                    navController.navigate(route = route)
                                },
                            )
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
                                viewModel = searchViewModel,
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

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientsCalculatedCorrectly() {
        repository.searchResults = listOf(
            TrackableFood(
                name = "Banana",
                imageUrl = null,
                caloriesPer100g = 150,
                proteinPer100g = 5,
                carbsPer100g = 50,
                fatPer100g = 1,
            )
        )

        val addedAmount = 150
        val expectedCalories = (1.5f * 150).roundToInt()
        val expectedProtein = (1.5f * 5).roundToInt()
        val expectedCarbs = (1.5f * 50).roundToInt()
        val expectedFat = (1.5f * 1).roundToInt()

        composeRule.onNodeWithText(text = "Add Breakfast").assertDoesNotExist()

        composeRule.onNodeWithContentDescription(label = "Breakfast").performClick()

        composeRule.onNodeWithText(text = "Add Breakfast").assertIsNotDisplayed()

        composeRule.onNodeWithText(text = "Add Breakfast").performClick()

        assertThat(
            navController.currentDestination?.route?.startsWith(prefix = Routes.SEARCH)
        ).isTrue()

        // Avoid Modifier.testTag(tag = "some_tag") in production code, if possible.
        composeRule.onNodeWithTag(testTag = "search_text_field").performTextInput(text = "banana")

        composeRule.onNodeWithContentDescription(label = "Search...").performClick()

        composeRule.onNodeWithText(text = "Carbs").performClick()

        // Custom Modifier.semantics { contentDescription = "description" }
        // is preferable to test tags, because it also provides accessibility.
        composeRule.onNodeWithContentDescription(label = "Amount")
            .performTextInput(text = addedAmount.toStr())

        composeRule.onNodeWithContentDescription(label = "Track").performClick()

        assertThat(
            navController.currentDestination?.route?.startsWith(prefix = Routes.TRACKER_OVERVIEW)
        ).isTrue()

        composeRule.onAllNodesWithText(text = expectedCalories.toStr())
            .onFirst()
            .assertIsNotDisplayed()

        composeRule.onAllNodesWithText(text = expectedProtein.toStr())
            .onFirst()
            .assertIsNotDisplayed()

        composeRule.onAllNodesWithText(text = expectedCarbs.toStr())
            .onFirst()
            .assertIsNotDisplayed()

        composeRule.onAllNodesWithText(text = expectedFat.toStr()).onFirst().assertIsNotDisplayed()
    }
}
