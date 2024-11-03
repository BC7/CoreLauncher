package com.sevenbitstudios.corelauncher

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap


data class App(
    val context: Context,
    val tileName: String,
    val packageName: String,
    val icon: Drawable
) {

    fun launch(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: return
        context.startActivity(intent)
    }

    @Composable
    fun renderTile(modifier: Modifier) {
        val displayMetrics = context.resources.displayMetrics;
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        val tileMaxWidth = (dpWidth / 4).dp
        TextButton(
            onClick = { launch(context) },
            modifier = modifier.width(tileMaxWidth)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = BitmapPainter(icon.toBitmap().asImageBitmap()),
                    contentDescription = null,
                    modifier = Modifier
                        .width(tileMaxWidth)
                        .height(tileMaxWidth)
                )
                Text(
//                    fontSize = 24.sp,
                    modifier = Modifier,
                    text = tileName,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )

            }
        }
    }
}

