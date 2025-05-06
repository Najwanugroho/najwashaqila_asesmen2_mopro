package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.R
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.CatatanDb
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.navigation.Screen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.ui.theme.Asesment2mopro_najwashaqilaisnanTheme
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util.SettingsDataStore
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val snackbarHostState = remember { SnackbarHostState() }
    var itemToDelete by remember { mutableStateOf<Catatan?>(null) }

    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFE1BEE7),
                    titleContentColor = Color(0xFF6A1B9A),
                ),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = Color(0xFF6A1B9A)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.RecycleBin.route)
                    },
                    containerColor = Color(0xFFE57373)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Recycle Bin",
                        tint = Color.White
                    )
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.FormBaru.route)
                    },
                    containerColor = Color(0xFFE1BEE7)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.tambah_catatan),
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        ScreenContent(
            showList = showList,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel,
            onDelete = { catatan ->
                viewModel.delete(catatan.id)
                itemToDelete = catatan
            }
        )

        itemToDelete?.let { item ->
            LaunchedEffect(key1 = item) {
                val result = snackbarHostState.showSnackbar(
                    message = "${item.moodLevel} dihapus",
                    actionLabel = "UNDO",
                    duration = SnackbarDuration.Long
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.restore(item.id)
                }
                itemToDelete = null
            }
        }
    }
}


@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    onDelete: (Catatan) -> Unit
) {
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(catatan = it, onClick = {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }, onDelete = {
                        onDelete(it)
                    })
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp, 12.dp, 12.dp, 100.dp)
            ) {
                items(data) {
                    GridItem(catatan = it, onClick = {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }, onDelete = {
                        onDelete(it)
                    })
                }
            }
        }
    }
}

@Composable
fun ListItem(catatan: Catatan, onClick: () -> Unit, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = catatan.moodLevel,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFBA68C8)
        )
        Text(
            text = catatan.deskripsi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = catatan.tanggal,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = "Hapus",
            color = Color.Red,
            modifier = Modifier.clickable { onDelete() }
        )
    }
}

@Composable
fun GridItem(catatan: Catatan, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF6D5)
        ),
        border = BorderStroke(1.dp, Color(0xFFCE93D8)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = catatan.moodLevel,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD48FB1)
            )
            Text(
                text = catatan.deskripsi,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = catatan.tanggal,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "Hapus",
                color = Color.Red,
                modifier = Modifier.clickable { onDelete() }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Asesment2mopro_najwashaqilaisnanTheme {
        MainScreen(navController = rememberNavController())
    }
}
