package com.example.tweetapp.screens.artistinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tweetapp.model.artistdetails.ArtistDetail
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.ui.component.ShowLoader
import com.example.tweetapp.ui.component.SubtitlePrimary
import com.example.tweetapp.ui.component.SubtitleSecondary
import com.example.tweetapp.utils.AppConstant
import com.example.tweetapp.utils.genderInString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun ArtistInfoScreen(artistId: Int?) {

    val artistViewModel = hiltViewModel<ArtistInfoViewModel>()
    val artistDetails = artistViewModel.artistDetails
    ShowLoader(showLoader = artistDetails.value)
    LaunchedEffect(key1 = Unit){
        artistViewModel.getArtistDetails(artistId)
    }

    Column {
        artistDetails.value?.let {
            ArtistDetails(it)
        }

    }

}

@Composable
fun ArtistDetails(artistDetails: DataState<ArtistDetail>) {
    if(artistDetails is DataState.Success<ArtistDetail>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(270.dp)
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    imageModel = {  AppConstant.IMAGE_URL.plus(artistDetails.data.profilePath) },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        contentDescription = "Movie detail",
                        colorFilter = null,
                    ),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(
                            duration = 800
                        )
                        +ShimmerPlugin(
                            baseColor = Color.Gray,
                            highlightColor = Color.Cyan
                        )
                    },
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = artistDetails.data.name, fontSize = 22.sp, color = Color.Black,
                        fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp)
                    )
                    Column(modifier = Modifier.padding(6.dp)) {
                        SubtitleSecondary(text = "Known For")
                        SubtitlePrimary(text = artistDetails.data.knownForDepartment)
                    }
                    Column(modifier = Modifier.padding(6.dp)) {
                        SubtitleSecondary(text = "Gender")
                        SubtitlePrimary(text = artistDetails.data.gender.genderInString())
                    }
                    Column(modifier = Modifier.padding(6.dp)) {
                        SubtitleSecondary(text = "Birthday")
                        SubtitlePrimary(text = artistDetails.data.birthday.toString())
                    }
                    Column(modifier = Modifier.padding(6.dp)) {
                        SubtitleSecondary(text = "Place of Birth")
                        SubtitlePrimary(text = artistDetails.data.placeOfBirth.toString())
                    }
                }
            }

            Text(
                text = "Biography", color = Color.DarkGray,
                fontSize = 22.sp, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.W700
            )
            Text(
                text = artistDetails.data.biography,
                color = Color.Gray,
                fontSize = 14.sp,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(10.dp)
            )

        }
    }
}
