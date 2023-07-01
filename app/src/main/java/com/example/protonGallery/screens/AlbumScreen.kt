package com.example.protonGallery.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.protonGallery.R
import com.example.protonGallery.data.Media
import com.example.protonGallery.ui.theme.Black
import com.example.protonGallery.ui.theme.White

@Composable
fun CreateAlbum(
    albumViewModel: AlbumViewModel,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val albumList = albumViewModel.albumState.collectAsState().value
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxHeight(),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(12.dp),
        verticalItemSpacing = 24.dp,
    ) {
        items(albumList.size) { index ->
            PhotoItem(albumList[index], navController)
        }
    }
}

@Composable
fun PhotoItem(
    media: Media,
    navController: NavController
) {
    val albumName = ""
    Card(
        modifier = Modifier
            .background(White, RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.MediaScreen.route + albumName) },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(media.uri))
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .requiredHeightIn(Dp.Unspecified, 200.dp)
                    .clickable { navController.navigate(Screen.MediaScreen.route + albumName) }
            )
            Text(
                text = media.albumName,
                textAlign = TextAlign.Left,
                color = Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(White)
                    .align(Alignment.Start)
                    .padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}