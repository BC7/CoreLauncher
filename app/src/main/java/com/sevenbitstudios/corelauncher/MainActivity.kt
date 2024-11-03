package com.sevenbitstudios.corelauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sevenbitstudios.corelauncher.ui.theme.CoreLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoreLauncherTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp),
                    color = Color.Transparent
                ) {
                    initAppTiles(Modifier)
                }
            }
        }
    }

    @Composable
    fun initAppTiles(modifier: Modifier) {

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val flags = PackageManager.ResolveInfoFlags.of(
            PackageManager.MATCH_ALL.toLong()
        )
        val activities: List<ResolveInfo> =
            this.packageManager.queryIntentActivities(intent, flags)
        val installedApps = activities.map { resolveInfo ->
            Log.d("APP INFO : ", resolveInfo.loadLabel(packageManager).toString())
            App(
                context = this.baseContext,
                tileName = resolveInfo.loadLabel(packageManager).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.loadIcon(packageManager)
            )
        }.sortedBy { a ->
            a.tileName
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
        ) {
            items(installedApps) { app ->
                app.renderTile(modifier = Modifier)
            }
        }
    }
}