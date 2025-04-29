package com.vakulenkoalex.moneygateway

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageView(vm: SmsViewModel) {
    val allSms by vm.allSms.observeAsState(listOf())
    Column {
        Button({ vm.deleteAllSms() }, Modifier.padding(8.dp)) {Text("Delete", fontSize = 22.sp)}
        MessageTableView(allSms)
    }
}