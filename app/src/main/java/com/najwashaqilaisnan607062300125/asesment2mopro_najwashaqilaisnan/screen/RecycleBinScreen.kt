package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.CatatanDb
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(CatatanDb.getInstance(LocalContext.current).dao)),

) {
    val deletedItems by viewModel.getDeletedItems().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (deletedItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Recycle Bin is empty")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(deletedItems, key = { it.id }) { item ->
                    DeletedItemCard(
                        item = item,
                        onRestore = { viewModel.restore(item.id) },
                        onPermanentDelete = { viewModel.delete(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun DeletedItemCard(
    item: Catatan,
    onRestore: () -> Unit,
    onPermanentDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)) // Soft warning background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mood: ${item.moodLevel}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.deskripsi)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Dihapus pada: ${item.deletedAt ?: "-"}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onRestore) {
                    Text("Restore")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onPermanentDelete) {
                    Text("Hapus Permanen", color = Color.Red)
                }
            }
        }
    }
}
