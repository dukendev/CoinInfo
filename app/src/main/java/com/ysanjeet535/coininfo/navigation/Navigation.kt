package com.ysanjeet535.coininfo.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ysanjeet535.coininfo.DetailScreenContent
import com.ysanjeet535.coininfo.MainDemoContent
import com.ysanjeet535.coininfo.view.viewmodel.MainViewModel


@Composable
fun NavGraph(viewModel: MainViewModel){
    val navController = rememberNavController()
    val actions = remember(navController){ MainActions(navController)}

    NavHost(navController = navController, startDestination = Screens.MainScreen.route){

        composable(Screens.MainScreen.route){
            MainDemoContent(mainViewModel = viewModel,actions = actions)

        }

        composable(
            route = "${Screens.DetailScreen.route}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
        )){
            DetailScreenContent(it.arguments?.getString("id")?:"Default",mainViewModel = viewModel)
        }

    }

}

class MainActions(navController: NavController){
    val gotoDetails : (String) -> Unit = {
        navController.navigate("${Screens.DetailScreen.route}/${it}")
    }
}