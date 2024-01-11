package dk.mathiasrossen.onboardingapp.ui.appbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun OnboardingBottomAppBar(navController: NavController) {
    val items = listOf(
        Screen.Sources,
        Screen.Favorites,
        Screen.About
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val menuItemColor = if (selected) MaterialTheme.colorScheme.secondary else Color.White
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.iconResourceId),
                        tint = menuItemColor,
                        contentDescription = null
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    if (selected) {
                        Text(text = stringResource(id = screen.titleResourceId), color = menuItemColor)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun BottomAppBarPreview() {
    OnboardingAppTheme {
        OnboardingBottomAppBar(rememberNavController())
    }
}