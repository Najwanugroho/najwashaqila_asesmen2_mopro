package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.ui.theme.Asesment2mopro_najwashaqilaisnanTheme
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util.ViewModelFactory

const val KEY_ID_CATATAN="idCatatan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)


    var moodLevel by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (id==null) return@LaunchedEffect
        val data=viewModel.getCatatan(id)?:return@LaunchedEffect
        moodLevel=data.moodLevel
        deskripsi=data.deskripsi
    }
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color(0xFF6A1B9A)
                        )
                    }
                },
                title={
                    if(id==null)
                        Text(text = stringResource(id=R.string.tambah_catatan))
                    else
                        Text(text = stringResource(id=R.string.edit_catatan))

                },
                colors= TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFE1BEE7),
                    titleContentColor = Color(0xFF6A1B9A),
                ),
                actions = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.Simpan),
                            tint = Color(0xFF6A1B9A)
                        )
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.mood_hari_ini),
            color = Color(0xFF6A1B9A),
            style = MaterialTheme.typography.titleMedium)

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            moodOptions.forEach { mood ->
                Button(
                    onClick = { onMoodChange(mood) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (moodLevel == mood) Color(0xFF6A1B9A) else Color(0xFFFCE4EC)
                    ),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.weight(1f)
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
    Asesment2mopro_najwashaqilaisnanTheme {
        DetailScreen(rememberNavController())
    }
}

