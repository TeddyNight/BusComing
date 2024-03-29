package com.github.teddynight.buscoming.ui.bus

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.teddynight.buscoming.R
import com.github.teddynight.buscoming.ui.nearby.timeInCart
import com.github.teddynight.buscoming.ui.theme.putCenter

@Composable
fun detailCard(viewModel: BusScreenViewModel = hiltViewModel()) {
    val bus = viewModel.bus.observeAsState()
    val line = bus.value!!.line
    Card(modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = line.name,
                    fontSize = 22.sp
                )
                Text("始发站: ${line.startSn}", fontSize = 16.sp)
                Text("终点站: ${line.endSn}", fontSize = 16.sp)
//                Row(horizontalArrangement = Arrangement.End,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .fillMaxWidth()) {
//                    Text(line.startSn, fontSize = 16.sp)
//                    Icon(Icons.Filled.ArrowForward,"到")
//                    Text(line.endSn, fontSize = 16.sp)
//                }
            }
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "首:${line.firstTime}, 末:${line.lastTime} 票价${line.price}",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun timeCard(viewModel: BusScreenViewModel = hiltViewModel()) {
    val arrivals = viewModel.arrivals.observeAsState()
    Card(modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()) {
            if (arrivals.value != null) {
                LazyRow(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()) {
                    items(arrivals.value!!) {
                        time ->
                        timeCart(time)
                    }
                }
            }
        }
    }
}

@Composable
fun timeCart(time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(4.dp)
        .height(72.dp)) {
        timeInCart(time)
        //Text("2站", fontSize = 14.sp)
    }
}

@Composable
fun stopList(viewModel: BusScreenViewModel = hiltViewModel()) {
    val bus = viewModel.bus.observeAsState()
    val stops = bus.value!!.stops
    val list = bus.value!!.list
    Column(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(stops) {
                    stop ->
                val order = stops.indexOf(stop)+1
                stopCart(stop,
                    order in list,
                    order == bus.value!!.line.order,
                    { viewModel.changeOrder(order) })
            }
        }
    }
}

@Composable
fun stopCart(name: String, hasBus: Boolean, isTarget: Boolean, onClick: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .width(with(LocalDensity.current) {
            32.sp.toDp()
        })) {
        Box(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        ) {
            if (hasBus) {
                Icon(ImageVector.vectorResource(R.drawable.bus), "到站")
            }
        }
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    32.sp.toDp()
                })
                .height(8.dp)
        )

        ClickableText(text= AnnotatedString(name),
            style = TextStyle(fontSize = 16.sp,
                color = if (isTarget) MaterialTheme.colors.primary else Color.Black,
                fontWeight = if (isTarget) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = Modifier
                .padding(4.dp)
                .width(with(LocalDensity.current) {
                    16.sp.toDp()
                }),
            onClick = onClick
            )
        }
}

@Composable
fun busScreen(viewModel: BusScreenViewModel = hiltViewModel(),
              navController: NavHostController) {
    val bus = viewModel.bus.observeAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text("线路详情") },
            navigationIcon = {
            IconButton(onClick = { navController.navigate("nearby") }) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
        })
    }, bottomBar = {
        BottomAppBar() {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                IconButton(onClick = { viewModel.changeDirection() }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(ImageVector.vectorResource(R.drawable.exchange), "换向")
                        Text("换向")
                    }
                }
                IconButton(onClick = { viewModel.refresh() }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Refresh, "刷新")
                        Text("刷新")
                    }
                }
            }
        }
    }) {
        if (bus.value == null) {
            putCenter{
                CircularProgressIndicator()
            }
        }
        else {
            Column() {
                detailCard()
                timeCard()
                stopList()
            }
        }
    }
}