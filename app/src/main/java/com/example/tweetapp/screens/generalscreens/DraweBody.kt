package com.example.tweetapp.screens.generalscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplaneTicket
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tweetapp.model.moviegenre.Genre

@Composable
fun DrawerBody(list: ArrayList<Genre>, drawerClose: (Int) -> Unit) {

    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top){
        items(list){
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp).clickable
            { drawerClose(it.id) }.fillMaxSize()) {
                Icon(Icons.Default.AirplaneTicket, contentDescription = "movie", tint = Color.Blue.copy(alpha = .5f))
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = it.name, fontSize = 20.sp)
            }
        }
    }

}