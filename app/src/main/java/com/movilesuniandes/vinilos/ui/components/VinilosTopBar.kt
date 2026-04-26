package com.movilesuniandes.vinilos.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.movilesuniandes.vinilos.ui.theme.VinilosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VinilosTopBar(
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null
                )
            }
        },
        title = {
            Text("Vinilos", maxLines = 1)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VinilosTopBarPreview() {
    VinilosTheme {
        VinilosTopBar()
    }
}
