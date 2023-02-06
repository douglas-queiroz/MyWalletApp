package com.douglasqueiroz.mywallet.android.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.douglasqueiroz.mywallet.android.feature.assetdetails.logic.AssetDetailsViewModel
import com.douglasqueiroz.mywallet.android.feature.assetdetails.ui.AssetDetailsScreen
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.AssetListViewModel
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.FixedIncomeAssetListViewModel
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.ShareAssetListViewModel
import com.douglasqueiroz.mywallet.android.feature.assetlist.ui.AssetListContent
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainViewModel
import com.douglasqueiroz.mywallet.android.feature.main.ui.MainContent
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SetupNavHost() {
    
    val navHostController = rememberNavController()
    
    NavHost(navController = navHostController, startDestination = Screen.MainScreen.route) {

        composable(Screen.MainScreen.route) {
            val viewModel: MainViewModel = koinViewModel()

            MainContent(
                state = viewModel.state,
                onRefreshBtnClick = viewModel::refresh,
                onAssetClick = {
                    navHostController.navigate("asset_list/${it.ordinal}")
                }
            )
        }

        composable(
            route = Screen.AssetListScreen.route,
            arguments = listOf(navArgument("asset_type") { type = NavType.IntType})
        ) {

            val viewModel: AssetListViewModel = it.arguments?.getInt("asset_type")?.let { intAssetType ->
                when(intAssetType) {
                    AssetType.SAVING_ACCOUNT.ordinal -> koinViewModel<FixedIncomeAssetListViewModel> {
                        parametersOf(FixedIncomeType.SAVING_ACCOUNT)
                    }
                    AssetType.STOCK.ordinal -> koinViewModel<ShareAssetListViewModel> {
                        parametersOf(ShareType.Stock)
                    }
                    AssetType.REIT.ordinal -> koinViewModel<ShareAssetListViewModel> {
                        parametersOf(ShareType.REIT)
                    }
                    AssetType.GOLD.ordinal -> koinViewModel<ShareAssetListViewModel> {
                        parametersOf(ShareType.GOLD)
                    }
                    AssetType.BITCOIN.ordinal -> koinViewModel<ShareAssetListViewModel> {
                        parametersOf(ShareType.BITCOIN)
                    }
                    else -> null
                }
            } ?: throw IllegalArgumentException()


            AssetListContent(state = viewModel.state, onClick = { assetId ->
                navHostController.navigate("asset_details/$assetId")
            })
        }

        composable(
            route = Screen.AssetDetails.route,
            arguments = listOf(navArgument(name = "asset_id") { type = NavType.StringType } )
        ) {
            val assetId = it.arguments?.getString("asset_id") ?: throw IllegalArgumentException()
            val viewModel = koinViewModel<AssetDetailsViewModel> { parametersOf(assetId) }

            AssetDetailsScreen(state = viewModel.state)
        }
    }
}

enum class Screen(val route: String) {
    MainScreen("main"), AssetListScreen("asset_list/{asset_type}"), AssetDetails("asset_details/{asset_id}")
}

enum class AssetType {
    SAVING_ACCOUNT, STOCK, REIT, GOLD, BITCOIN
}