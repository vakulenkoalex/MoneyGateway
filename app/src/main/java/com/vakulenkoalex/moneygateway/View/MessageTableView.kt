package com.vakulenkoalex.moneygateway.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vakulenkoalex.moneygateway.Room.Message

@Composable
fun MessageTableView(allMessage:List<Message>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ MessageTitleRow() }
        items(allMessage){ u -> MessageRow(u) }
    }
}

@Composable
fun MessageRow(message: Message) {
    Row(Modifier .fillMaxWidth().padding(5.dp)) {
        Text(message.id.toString(), Modifier.weight(0.1f), fontSize = 22.sp)
        Text(message.text, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(message.sender, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(message.type.displayName, Modifier.weight(0.2f), fontSize = 22.sp)
    }
}

@Composable
fun MessageTitleRow() {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        Text("Id", color = Color.White,modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Text", color = Color.White,modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Sender", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Type", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Spacer(Modifier.weight(0.2f))
    }
}