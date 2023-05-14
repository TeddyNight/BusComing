package com.github.teddynight.buscoming.ui.nearby

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.teddynight.buscoming.R

@Composable
fun lineCart(index: Int, viewModel: NearbyScreenViewModel = viewModel(),
             navController: NavHostController = rememberNavController()
) {
    val lineList = viewModel.lines.observeAsState()
    val linePair = lineList.value!![index]
    var direction = remember { mutableStateOf(true) }
    val line = if(direction.value) linePair[0] else linePair[1]
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .clickable {
            navController.navigate("bus/"+linePair[0].id+"/"+linePair[1].id)
        }) {
        Column() {
            bigCartText(line.name)
            smallCartText("终点站："+line.endSn)
        }
        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {
            timeInCart(viewModel.getWaitingTime(index,direction.value!!));
            Divider(color = Color.Gray,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .width(1.dp))
            IconButton(modifier = Modifier
                .size(36.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically)
                ,onClick = { direction.value = !direction.value }) {
                Icon(ImageVector.vectorResource(R.drawable.exchange),"切换方向")
            }
        }
    }
}