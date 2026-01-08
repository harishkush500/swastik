// 📁 MainActivity.kt
package com.swastikbagmanufacturing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.swastikbagmanufacturing.ui.theme.SwastikBagManufacturingTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwastikBagManufacturingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showSplash = false
    }

    AnimatedVisibility(visible = showSplash, enter = fadeIn(), exit = fadeOut()) {
        SplashScreen()
    }

    if (!showSplash) {
        HomeScreen()
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF00695C)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Swastik Bag Manufacturing", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Swastik Bag Manufacturing!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { showProducts = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Products")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showCalculator = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Bill Calculator")
        }
    }
}

var showProducts by mutableStateOf(false)
var showCalculator by mutableStateOf(false)

@Composable
fun ProductScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Our Products", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        repeat(3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Bag Type XYZ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("High-quality bag suitable for all purposes.")
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(context: android.content.Context) {
    var gram by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(0f) }
    var rate by remember { mutableStateOf(0f) }

    val bagWeight = (gram * size) / 1000
    val netPrice = bagWeight * rate

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bill Calculator", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = if (gram == 0f) "" else gram.toString(),
            onValueChange = { gram = it.toFloatOrNull() ?: 0f },
            label = { Text("Gram") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = if (size == 0f) "" else size.toString(),
            onValueChange = { size = it.toFloatOrNull() ?: 0f },
            label = { Text("Size") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = if (rate == 0f) "" else rate.toString(),
            onValueChange = { rate = it.toFloatOrNull() ?: 0f },
            label = { Text("Rate") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bag Weight: ${bagWeight} KG")
        Text("Net Price: ₹${netPrice}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val message = "Gram: $gram\nSize: $size\nRate: $rate\nBag Weight: ${bagWeight} KG\nNet Price: ₹${netPrice}"
            val url = "https://wa.me/9399988455?text=${Uri.encode(message)}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            ContextCompat.startActivity(context, intent, null)
        }) {
            Text("Share on WhatsApp")
        }
    }
}
