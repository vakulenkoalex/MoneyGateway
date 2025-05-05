package com.vakulenkoalex.moneygateway.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vakulenkoalex.moneygateway.R
import com.vakulenkoalex.moneygateway.SaveHelper

@Composable
fun MainView(viewModel: MainViewModel) {
    val allMessage by viewModel.allMessage.observeAsState(listOf())
    var debugMode by remember { mutableStateOf(false) }

    Column {
        Button({ viewModel.deleteAllMessage() }, Modifier.padding(8.dp))
        { Text(stringResource(R.string.delete_message), fontSize = 22.sp) }
        Row{
            Checkbox(
                checked = debugMode,
                onCheckedChange = {
                    debugMode = it
                    SaveHelper.setDebugMode(it)
                }
            )
            Text(stringResource(R.string.save_all_message),
                fontSize = 20.sp,
                modifier = Modifier.padding(12.dp))
        }
        MessageTableView(allMessage)
    }
}