package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(){
    var moodLevel by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                title={
                        Text(text = stringResource(id= R.string.tambah_catatan))


                },
                colors= TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
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

