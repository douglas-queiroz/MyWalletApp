package com.douglasqueiroz.mywallet.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.douglasqueiroz.mywallet.android.home.MainStateView
import com.douglasqueiroz.mywallet.android.home.MainViewModel
import com.douglasqueiroz.mywallet.android.home.ShareItem
import com.douglasqueiroz.mywallet.domain.dto.OverallReportDto
import com.douglasqueiroz.mywallet.domain.usecases.CalculateOverallReportUseCase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadReport()
    }

    @Composable
    fun MainContent(
        viewModel: MainViewModel
    ) {
        MaterialTheme {
            Scaffold(
                topBar = { TopBar() },
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    MainViewList(
                        total = viewModel.state.total,
                        shareItemList = viewModel.state.shareItemList
                    )
                    Loading(show = viewModel.state.showLoadingProgress)
                }
            }
        }
    }

    @Composable
    fun TotalView(total: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(34.dp)
        ) {
            Text(
                text = stringResource(R.string.total),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = total,
                fontSize = 22.sp
            )
        }
    }

    @Composable
    fun Loading(show: Boolean) {
        if (show) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun MainViewList(total: String, shareItemList: List<ShareItem>) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TotalView(total = total)
            LazyColumn(content = {
                items(shareItemList) { item ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.surface)
                    ) {
                        Box(modifier = Modifier
                            .height(100.dp)
                            .padding(16.dp)
                        ) {
                            Column {
                                Text(item.name)
                                Spacer(modifier = Modifier.weight(1f))
                                Row(
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Total: ${item.total} (${item.percentage})")
                                }

                            }
                        }
                    }
                }
            })
        }
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            title = {
                Text(
                    text = "My Wallet",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
    }

    @Preview
    @Composable
    fun Preview() {
        val viewModel = MainViewModel(
            initialStateView = MainStateView(
                shareItemList = arrayListOf(
                    ShareItem("Stock", "€100", "30%"),
                    ShareItem("Fixed income", "€400", "50%"),
                    ShareItem("Saves", "€300", "25%"),
                    ShareItem("Saves", "€300", "25%"),
                    ShareItem("Saves", "€300", "25%"),
                    ShareItem("Saves", "€300", "25%"),
                    ShareItem("Saves", "€300", "25%"),
                ),
                total = "€100.00"
            ),
            loadOverallReportUseCase = object : CalculateOverallReportUseCase {
                override suspend fun execute(): List<OverallReportDto> {
                    TODO("Not yet implemented")
                }
            }
        )

        MainContent(viewModel)
    }
}
