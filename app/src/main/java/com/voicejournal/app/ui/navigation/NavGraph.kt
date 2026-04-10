package com.voicejournal.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.voicejournal.app.ui.categories.CategoriesScreen
import com.voicejournal.app.ui.home.HomeScreen
import com.voicejournal.app.ui.logdetail.LogDetailScreen
import com.voicejournal.app.ui.people.PeopleListScreen
import com.voicejournal.app.ui.people.PersonDetailScreen
import com.voicejournal.app.ui.record.FinalizeDraftScreen
import com.voicejournal.app.ui.record.RecordFlowScreen
import com.voicejournal.app.ui.search.SearchScreen
import com.voicejournal.app.ui.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Home, modifier = modifier) {

        composable<Home> {
            HomeScreen(
                onNavigateToLogDetail = { voiceLogId ->
                    navController.navigate(LogDetail(voiceLogId))
                },
                onNavigateToFinalizeDraft = { draftId ->
                    navController.navigate(FinalizeDraft(draftId))
                }
            )
        }

        composable<RecordFlow> {
            RecordFlowScreen(
                onSaved = {
                    navController.popBackStack(Home, inclusive = false)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<PeopleList> {
            PeopleListScreen(
                onNavigateToPersonDetail = { personId ->
                    navController.navigate(PersonDetail(personId))
                }
            )
        }

        composable<PersonDetail> {
            PersonDetailScreen(
                onBack = { navController.popBackStack() },
                onNavigateToLogDetail = { voiceLogId ->
                    navController.navigate(LogDetail(voiceLogId))
                }
            )
        }

        composable<LogDetail> {
            LogDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<FinalizeDraft> {
            FinalizeDraftScreen(
                onDone = {
                    navController.popBackStack(Home, inclusive = false)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Categories> {
            CategoriesScreen()
        }

        composable<Search> {
            SearchScreen(
                onNavigateToLogDetail = { voiceLogId ->
                    navController.navigate(LogDetail(voiceLogId))
                },
                onNavigateToFinalizeDraft = { draftId ->
                    navController.navigate(FinalizeDraft(draftId))
                }
            )
        }

        composable<Settings> {
            SettingsScreen()
        }
    }
}
