package ru.sergey.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.sergey.health.ui.theme.HealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun test()
{
    Column(modifier = Modifier.background(Color.Red).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.End) {
        Text(text = "Hello METANIT.COM!", fontSize = 28.sp)
        Text(text = "Hello METANIT.COM!", fontSize = 28.sp)
        Text(text = "Hello METANIT.COM!", fontSize = 28.sp)
        Row(modifier = Modifier.background(Color.Gray).fillMaxHeight(0.5f)) {
            Text(text = "Hello ", fontSize = 28.sp)
            Text(text = "Hello ", fontSize = 28.sp)
            Text(text = "Hello ", fontSize = 28.sp)
        }
    }

}