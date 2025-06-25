package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.R
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.CatatanDb
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.ui.theme.Asesment2mopro_najwashaqilaisnanTheme
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util.ViewModelFactory

const val KEY_ID_CATATAN = "idCatatan"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long? = null,
    isDarkTheme: MutableState<Boolean>
) {
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    remember { SnackbarHostState() }
    var itemToDelete by remember { mutableStateOf<Catatan?>(null) }

    var moodLevel by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var catatan by remember { mutableStateOf<Catatan?>(null) }

    LaunchedEffect(true) {
        if (id != null) {
            val data = viewModel.getCatatan(id)
            catatan = data
            data?.let {
                moodLevel = it.moodLevel
                deskripsi = it.deskripsi
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color(0xFF6A1B9A)
                        )
                    }
                },
                title = {
                    Text(
                        text = if (id == null)
                            stringResource(R.string.tambah_mood)
                        else
                            stringResource(R.string.edit_mood),
                        color = Color(0xFF6A1B9A)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFE1BEE7),
                    titleContentColor = Color(0xFF6A1B9A),
                ),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("🌞 / 🌙")
                        Switch(
                            checked = isDarkTheme.value,
                            onCheckedChange = { isDarkTheme.value = it }
                        )
                    }

                    IconButton(onClick = {
                        if (moodLevel.isBlank() || deskripsi.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.isi_dulu), Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) viewModel.insert(moodLevel, deskripsi)
                        else viewModel.update(id, moodLevel, deskripsi)

                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan_mood),
                            tint =  Color(0xFF6A1B9A)
                        )
                    }

                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormCatatan(
            moodLevel = moodLevel,
            onMoodChange = { moodLevel = it },
            desc = deskripsi,
            onDescChange = { deskripsi = it },
            modifier = Modifier.padding(padding)
        )
        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false }) {
                showDialog = false
                catatan?.let {
                    itemToDelete = Catatan(id = id, moodLevel = moodLevel, deskripsi = deskripsi, tanggal = it.tanggal)
                }
                viewModel.delete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint =  Color(0xFF6A1B9A)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.hapus)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}


@Composable
fun FormCatatan(
    moodLevel: String,
    onMoodChange: (String) -> Unit,
    desc: String,
    onDescChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val moodOptions = listOf("Senang", "Sedih", "Kesal", "Marah")
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.mood_hari_ini),
            color = Color(0xFF6A1B9A),
            style = MaterialTheme.typography.titleMedium)

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moodOptions.forEach { mood ->
                Button(
                    onClick = { onMoodChange(mood) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (moodLevel == mood) Color(0xFFFFB6C1) else Color(0xFFFCE4EC)
                    ),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = mood)
                }
            }
        }

        OutlinedTextField(
            value = desc,
            onValueChange = onDescChange,
            label = { Text(text = stringResource(R.string.kenapa_merasa)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    val isDark = rememberSaveable { mutableStateOf(false) }
    Asesment2mopro_najwashaqilaisnanTheme(darkTheme = isDark.value) {
        DetailScreen(navController = rememberNavController(), isDarkTheme = isDark)
    }
}

