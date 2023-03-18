package com.example.kotlingdemoapp.ui.hamburgerMenu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlingdemoapp.ui.theme.DemoTheme

@Preview
@Composable
fun HamburgerMenuContent() {
    DemoTheme(true) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant)) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Header("Nexus")
            BodyItem("Change Space")
        }
    }
}

@Composable
private fun Header(name : String = "") {
    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background).height(74.dp),
        verticalAlignment = CenterVertically,) {
        Text(
            name,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun BodyItem(name : String = "") {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Text(
            name,
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(Modifier.weight(1f))
        BodyItemHeaderButton(
            expanded = expanded,
            onClick = { expanded = !expanded })
    }
    if (expanded) {
        BodyItemContent()
    }
}

@Composable
fun BodyItemContent() {
    Row(modifier = Modifier
        .padding(vertical = 8.dp,
            horizontal =  26.dp)
        .fillMaxWidth()) {
        Text(
            "ITEMMM",
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun BodyItemHeaderButton(expanded: Boolean, onClick: () -> Unit,) {
    IconButton( onClick = onClick ) {
        Icon (
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Expand button",
        )
    }
}
