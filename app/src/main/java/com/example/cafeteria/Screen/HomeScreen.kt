package com.example.cafeteria.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafeteria.R

@Composable
fun HomeScreen() {

    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf(1) }

    val bocadillos = listOf("Jamón", "Tortilla", "Pollo", "Vegetal")
    var bocadilloSeleccionado by remember { mutableStateOf(bocadillos[0]) }
    var expanded by remember { mutableStateOf(false) }

    val precioUnitario = 10
    var pedidoActual by remember { mutableStateOf<String?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    Scaffold { padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cafetería Politécnica",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Bocadillo: $bocadilloSeleccionado")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                bocadillos.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            bocadilloSeleccionado = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (cantidad > 1) cantidad-- }) { Text("-") }
            Text(text = cantidad.toString(), modifier = Modifier.padding(horizontal = 16.dp))
            Button(onClick = { cantidad++ }) { Text("+") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank()) {
                    val total = cantidad * precioUnitario
                    pedidoActual = "$cantidad × $bocadilloSeleccionado - ${total}€"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir pedido (10€)")
        }

        Spacer(modifier = Modifier.height(24.dp))

        pedidoActual?.let {
            val imagenBocadillo = when (bocadilloSeleccionado) {
                "Jamón" -> R.drawable.jamon
                "Tortilla" -> R.drawable.tortilla
                "Pollo" -> R.drawable.pollo
                "Vegetal" -> R.drawable.vegetal
                else -> R.drawable.jamon
            }

            Text(
                text = "Lista de pedidos (1) para $nombre",
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = imagenBocadillo),
                    contentDescription = "Imagen bocadillo",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = "$cantidad × $bocadilloSeleccionado", fontSize = 16.sp)
                    Text(text = "${cantidad * precioUnitario}€", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    mostrarDialogo = true
                    pedidoActual = null
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Confirma pedido", fontSize = 16.sp)
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                confirmButton = {
                    Button(onClick = { mostrarDialogo = false }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Confirmación del pedido") },
                text = {
                    Text("Gracias por realizar un pedido en la Cafetería del Politécnico")
                }
            )
        }
        }
    }
}
