package com.ysanjeet535.coininfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ysanjeet535.coininfo.data.dto.CoinItem
import com.ysanjeet535.coininfo.data.dto.CoinMetaData
import com.ysanjeet535.coininfo.navigation.MainActions
import com.ysanjeet535.coininfo.navigation.NavGraph
import com.ysanjeet535.coininfo.ui.theme.CoinInfoTheme
import com.ysanjeet535.coininfo.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val coinApi = RetrofitHelper.getInstance().create(CoinApi::class.java)
//        val repository = CoinRepository(coinApi)
//        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        val mainViewModel by viewModels<MainViewModel>()
        setContent {
            CoinInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.Magenta.copy(alpha = 0.3f)) {
                    NavGraph(viewModel = mainViewModel)
                }
            }
        }
    }
}



@ExperimentalCoilApi
@Composable
fun DetailScreenContent(id : String,mainViewModel: MainViewModel){
    mainViewModel.getMetadata(id)
    val coinMetaData = mainViewModel.coinMetadata.observeAsState()
    val backgroundColor = listOf(Color.Cyan,Color.Magenta)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(backgroundColor),alpha = 0.5f)) {
        coinMetaData.value?.let {
            val coinMetaOne = it[0]
            val imagePainter = rememberImagePainter(data = coinMetaOne.logo_url){
                crossfade(1000)
                transformations(
                    CircleCropTransformation()
                )
            }
            val scrollState = rememberScrollState(0)
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = coinMetaOne.id,fontWeight = FontWeight.ExtraBold)
                    Image(painter = imagePainter, contentDescription = null,modifier = Modifier.size(100.dp))
                }

                Divider(modifier = Modifier.padding(20.dp))
                Text(
                    text = coinMetaOne.description,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(200.dp)
                        .verticalScroll(state = scrollState,enabled = true)
                )
            }
        }
    }
}

@Composable
fun MainDemoContent(mainViewModel: MainViewModel,actions: MainActions){
    val coinList : List<CoinItem> by  mainViewModel.coins.observeAsState(initial = listOf())
    LazyColumn{
        items(coinList){
            item ->
            CoinPriceInfo(coinItem = item,actions = actions,mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun CoinPriceInfo(coinItem: CoinItem, actions: MainActions,mainViewModel: MainViewModel){
    val colors = listOf(Color.Cyan, Color.Magenta,Color.Yellow)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            actions.gotoDetails(coinItem.currency)
        }
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(brush = Brush.linearGradient(colors),alpha = 0.7f)
                .padding(30.dp)
            ,
        ) {
            Text(text = coinItem.currency)
            Text(text = "${coinItem.price} $",fontWeight = FontWeight.Bold)
        }

    }
}


