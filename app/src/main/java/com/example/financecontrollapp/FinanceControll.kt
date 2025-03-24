package com.example.financecontrollapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FinanceControll() {

    var selectedMonth by remember { mutableStateOf(LocalDate.now().month.name.toLowerCase().capitalize())}
    var editDialogOpen by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingName by remember { mutableStateOf("") }
    var editingValor by remember { mutableStateOf("") }
    var checado by remember { mutableStateOf(false) }
    var qtdParcelas by remember { mutableStateOf(0) }
    val months = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )

    val lista = remember {
        mutableStateListOf(
            Gasto(nome = "Aluguel", valor = 2000.00, parcelado = false, parcelas = 1, mesInicio = months[2]),
            Gasto(nome = "Carro", valor = 500.00, parcelado = false, parcelas = 1, mesInicio = months[2]),
            Gasto(nome = "Internet", valor = 129.90, parcelado = false, parcelas = 1, mesInicio = months[2]),
            Gasto(nome = "Cadeira", valor = 800.00, parcelado = true, parcelas = 4, mesInicio = months[2])
        )
    }

    val listaFiltrada = lista.filter { gasto ->
        val selectedMonthIndex = months.indexOf(selectedMonth)
        val firsMonthIndex = months.indexOf(gasto.mesInicio)
        val lastMonthIndex = firsMonthIndex + gasto.parcelas-1
        if (firsMonthIndex < 0 || selectedMonthIndex < 0) return@filter false
        selectedMonthIndex >= firsMonthIndex && selectedMonthIndex <= lastMonthIndex
    }

    Column(){
        Meses(selectedMonth = selectedMonth, onMonthSelected = { selectedMonth = it })
        LazyColumn {
            itemsIndexed(listaFiltrada) { position, _ ->
                GastoItem(
                    position,
                    listaFiltrada,
                    onDelete = { lista.removeAt(position) },
                    onEdit = {
                        editDialogOpen = true
                        editingIndex = position
                        editingName = lista[position].nome.toString()
                        editingValor = lista[position].valor.toString()
                        checado = lista[position].parcelado
                        qtdParcelas = lista[position].parcelas
                    }
                )
            }
        }
    }

    if (editDialogOpen) {
        Dialog(
            onDismissRequest = {editDialogOpen = false}
        ) {
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .height(300.dp)
            ) {
                Text(text = "Edite o gasto")
                OutlinedTextField(
                    value = editingName,
                    onValueChange = { editingName = it }
                )
                OutlinedTextField(
                    value = editingValor,
                    onValueChange = { editingValor = it })
                Checkbox(checked = checado, onCheckedChange = {checado = it} )
                Text(text = "Parcelas: $qtdParcelas")
                Row() {
                    Button(
                        onClick = {
                            editingIndex?.let { index ->
                                lista[index] = lista[index].copy(
                                    nome = editingName,
                                    valor = editingValor.toDouble(),
                                    parcelado = checado
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
                    .height(300.dp)
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
                Checkbox(checked = checado, onCheckedChange = {checado = it} )
                if(checado == true) {
                    OutlinedTextField(value = qtdParcelas.toString(), onValueChange = {qtdParcelas = it.toInt()})
                }
                Row() {
                    Button(
                        onClick = {
                            lista.add(Gasto(nome = editingName, valor = editingValor.toDouble(), parcelado = checado, parcelas = qtdParcelas));
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