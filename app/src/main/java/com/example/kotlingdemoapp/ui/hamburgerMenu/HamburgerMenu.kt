package com.example.kotlingdemoapp.ui.hamburgerMenu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlingdemoapp.ui.theme.DemoTheme

@Preview
@Composable
fun HamburgerMenu() {
    DemoTheme(true) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.primaryVariant)) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Header("Nexus")
            ButtonItem(icon = Icons.Rounded.Folder, text = "Folders")
            DividerItem()
            FoldableItem("Change Space")
        }
    }
}

@Composable
fun ButtonItem(modifier: Modifier = Modifier, onItemClicked : () -> Unit = {}, icon : ImageVector? = null, text : String) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable(onClick = onItemClicked),
        verticalAlignment = CenterVertically
    ) {
        val paddingSizeModifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(24.dp)
        if (icon != null) {
            Icon (
                imageVector = icon,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = "Button icon",
            )
        }
        else
            Spacer(modifier = paddingSizeModifier)

        Text(
            text,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.36f)
    )
}

@Composable
private fun Header(name : String = "") {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .height(102.dp)
        .padding(16.dp),
        verticalAlignment = CenterVertically) {
        Column() {
            Text(
                name,
                color = MaterialTheme.colors.onPrimary,
            )
            ProfileHeader()
        }
    }
}

@Composable
private fun ProfileHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .background(MaterialTheme.colors.secondary)
    ) {
        Text("Profiel hier weyyooww maat ziek bro inshllah")
    }
}

@Composable
private fun FoldableItem(name : String = "") {
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
        FoldableItemHeader(
            expanded = expanded,
            onClick = { expanded = !expanded })
    }
    if (expanded) {
        FoldableItemContent()
    }
}

@Composable
fun FoldableItemContent() {
    Row(modifier = Modifier
        .padding(
            vertical = 8.dp,
            horizontal = 26.dp
        )
        .fillMaxWidth()
        .background(MaterialTheme.colors.secondary)
    ) {
        ButtonItem(text = "BHHOMBOCLATTTTTT")
    }
}

@Composable
fun FoldableItemHeader(expanded: Boolean, onClick: () -> Unit,) {
    IconButton( onClick = onClick ) {
        Icon (
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Expand button",
        )
    }
}
