package com.example.protonGallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.protonGallery.data.BottomNavigationItem
import com.example.protonGallery.data.DataProvider
import com.example.protonGallery.data.Media
import com.example.protonGallery.data.NavigationDrawerItem
import com.example.protonGallery.database.GalleryDatabase
import com.example.protonGallery.permission.PermissionManager
import com.example.protonGallery.permission.PermissionUtil
import com.example.protonGallery.screens.AlbumViewModel
import com.example.protonGallery.screens.CategoryScreen
import com.example.protonGallery.screens.CreateAlbum
import com.example.protonGallery.screens.MediaScreen
import com.example.protonGallery.screens.Screen
import com.example.protonGallery.ui.theme.FabColor
import com.example.protonGallery.ui.theme.White
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var drawerNavItems: List<NavigationDrawerItem>
    private lateinit var bottomNavItems: List<BottomNavigationItem>
    private lateinit var database: GalleryDatabase

    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
        "AutoboxingStateValueProperty"
    )
    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        albumViewModel = ViewModelProvider(this)[AlbumViewModel::class.java]

        albumViewModel.albumState.value =
            GalleryDatabase.getDataBase(applicationContext).mediaDao().getAlbumList()
Log.i("asdfghjkl",GalleryDatabase.getDataBase(applicationContext).mediaDao().getNoOfPhotos("Camera").toString())
        setContent {
            navController = rememberNavController()
            bottomNavItems = DataProvider.getDataBaseProvider(applicationContext)
                .getBottomNavigationItemList(applicationContext)
            drawerNavItems = DataProvider.getDataBaseProvider(applicationContext)
                .getDrawerNavigationItemList(applicationContext)
            Scaffold(topBar = {
                TopAppBar(
                    actions = {
                        IconButton(
                            onClick = {
                                if (PermissionManager.checkPermission(
                                        applicationContext,
                                        PermissionUtil.CAMERA_PERMISSION
                                    )
                                )
                                    registerForActivityResult(ActivityResultContracts.TakePicture()) { isPictureTaken: Boolean ->
                                        if (isPictureTaken)
                                            GlobalScope.launch {
                                                GalleryDatabase.getDataBase(applicationContext)
                                                    .mediaDao().update(Media(0, "", ""))
                                            }
                                        else
                                            PermissionManager.getCameraLauncher(this@MainActivity)
                                                .launch(PermissionUtil.CAMERA_PERMISSION)
                                    }
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = "Camera"
                            )
                        }
                    },
                    title = { Text(text = "Proton") },
                    navigationIcon = {},
                )
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    containerColor = FabColor,
                    shape = CircleShape,
                    contentColor = White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add), contentDescription = ""
                    )
                }
            }, bottomBar = {
                val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
                BottomAppBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    bottomNavItems.forEachIndexed { index, item ->

                        NavigationBarItem(alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    painter = painterResource(item.id),
                                    "Favourite",
                                    tint = if (selectedIndex.value == index) item.selectedColor else item.unSelectedColor
                                )
                            },
                            label = { Text(item.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            colors = NavigationBarItemDefaults.colors(White),
                            onClick = {
                                selectedIndex.value = index
                                navController.navigate(bottomNavItems[index].screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }
                }
            }, content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.AlbumScreen.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(
                        Screen.AlbumScreen.route
                    ) {
                        CreateAlbum(
                            albumViewModel,
                            paddingValues,
                            navController
                        )
                    }
                    composable(
                        Screen.MediaScreen.route
                    ) { MediaScreen() }
                    composable(
                        Screen.CategoryScreen.route
                    ) { CategoryScreen(navController, DataProvider.getDataBaseProvider(applicationContext).getCategoryList(applicationContext)) }
                }
            })
        }
        if (!PermissionManager.checkPermission(
                applicationContext, PermissionUtil.READ_STORAGE
            )
        ) PermissionManager.permissionManager.getStorageLauncher(this)
            .launch(PermissionUtil.READ_STORAGE)
        database = GalleryDatabase.getDataBase(applicationContext)
    }
}