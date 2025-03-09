package com.example.financecontrollapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.financecontrollapp.model.Gasto

@Composable
fun FinanceControll() {

    var editDialogOpen by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingName by remember { mutableStateOf("") }
    var editingValor by remember { mutableStateOf("") }

    val lista = remember {
        mutableStateListOf(
            Gasto(nome = "Aluguel", valor = 2000.00),
            Gasto(nome = "Carro", valor = 500.00),
            Gasto(nome = "Internet", valor = 129.90)
        )
    }

    LazyColumn {
        itemsIndexed(lista) { position, _ ->
            GastoItem(
                position,
                lista,
                onDelete = { lista.removeAt(position) },
                onEdit = {
                    editDialogOpen = true
                    editingIndex = position
                    editingName = lista[position].nome.toString()
                    editingValor = lista[position].valor.toString()
                }
            )
        }
    }

    if (editDialogOpen) {
        Dialog(
            onDismissRequest = {editDialogOpen = false}
        ) {
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .height(200.dp)
            ) {
                Text(text = "Edite o gasto")
                OutlinedTextField(
                    value = editingName,
                    onValueChange = { editingName = it }
                )
                OutlinedTextField(
                    value = editingValor,
                    onValueChange = { editingValor = it })
                Row() {
                    Button(
                        onClick = {
                            editingIndex?.let { index ->
                                lista[index] = lista[index].copy(
                                    nome = editingName,
                                    valor = editingValor.toDouble()
                                )
                            }
                            editDialogOpen = false
                            editingName = ""
                            editingValor = ""
                        }
                    ) {
                        Text(text = "Salvar")
                    }
                    Button(onClick = {
                        editDialogOpen = false
                        editingName = ""
                        editingValor = ""
                    }) {
                        Text(text = "Fechar")
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        FloatingActionButton(
            onClick = { isDialogOpen = true },
            shape = RoundedCornerShape(30.dp),
            containerColor =  Color(0xFF206D00),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }

    if(isDialogOpen) {
        Dialog(
            onDismissRequest = { isDialogOpen = false }
        ) {
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .height(200.dp)
            ) {
                Text("Adicione um gasto")
                OutlinedTextField(
                    value = editingName,
                    onValueChange = { editingName = it }
                )
                OutlinedTextField(
                    value = editingValor,
                    onValueChange = { editingValor = it }
                )
                Row() {
                    Button(
                        onClick = {
                            lista.add(Gasto(nome = editingName, valor = editingValor.toDouble()));
                            isDialogOpen = false;
                            editingName = ""}
                    ) {
                        Text(text = "Adicionar")
                    }
                    Button(
                        onClick = { isDialogOpen = false }
                    ) {
                        Text(text = "Fechar")
                    }
                }
            }
        }
    }
}