package com.example.protonGallery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.protonGallery.R
import com.example.protonGallery.data.Category
import com.example.protonGallery.ui.theme.Black
import com.example.protonGallery.ui.theme.White

@Composable
fun CategoryScreen(
    navController: NavController = rememberNavController(),
    categoryList: List<Category>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .background(White)
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(48.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        items(categoryList.size) { index ->
            CategoryItem(navController, categoryList[index])
        }
    }
}

@Composable
fun CategoryItem(navController: NavController, category: Category = Category(R.drawable.category, "Category")) {
    Card(
        modifier = Modifier
            .background(White, RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.MediaScreen.route) },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(4.dp)
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.id)
                    .size(Size.ORIGINAL)
                    .scale(Scale.FIT)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Text(
                text = category.title,
                textAlign = TextAlign.Left,
                color = Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(White)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}