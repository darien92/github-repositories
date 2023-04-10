package com.darien.navigation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.darien.core_ui.AppScreens
import kotlinx.coroutines.delay
import com.darien.navigation.R
import com.darien.navigation.util.Constants
import com.darien.navigation.util.SplashScreenDims

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LaunchedEffect(key1 = true){
    delay(Constants.SPLASH_SCREEN_DELAY)
    navController.popBackStack()
    navController.navigate(AppScreens.SearchScreen.name)
}
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(SplashScreenDims.logo_radius),
            painter = painterResource(id = R.drawable.github_icon),
            contentDescription = stringResource(
                id = R.string.github_icon
            ),
        )
    }
}