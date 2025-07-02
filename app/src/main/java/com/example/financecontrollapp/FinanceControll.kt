package com.example.financecontrollapp

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.derivedStateOf
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
import com.example.financecontrollapp.model.Expense
import java.time.LocalDate

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FinanceControll() {
    val TAG = "teste"
    var selectedMonth by remember { mutableStateOf(LocalDate.now().month.name.toLowerCase().capitalize())}
    Log.i(TAG, "Mes selecionado " + selectedMonth)
    var editDialogOpen by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingName by remember { mutableStateOf("") }
    var editingValue by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var installmentsNumber by remember { mutableStateOf("") }
    val months = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )

    val list = remember {
        mutableStateListOf(
            Expense(name = "Aluguel", value = 2000.00, installment = false, installments = 1, beginMonth = months[6]),
            Expense(name = "Carro", value = 500.00, installment = false, installments = 1, beginMonth = months[6]),
            Expense(name = "Internet", value = 129.90, installment = false, installments = 1, beginMonth = months[6]),
            Expense(name = "Cadeira", value = 800.00, installment = true, installments = 4, beginMonth = months[6])
        )
    }

    val selectedMonthIndex = months.indexOf(selectedMonth)
    //val  =

    val filteredList by derivedStateOf {
        list.filter { expense ->
            if (expense.installment && expense.installments > 0) {
                val firstMonthIndex = months.indexOf(expense.beginMonth)
                val lastMonthIndex = firstMonthIndex + expense.installments - 1
                selectedMonthIndex in firstMonthIndex..lastMonthIndex
            } else {
                expense.beginMonth == selectedMonth
            }
        }
    }



    Column(){
        Months(selectedMonth = selectedMonth, onMonthSelected = { selectedMonth = it }, selectedMonthIndex = selectedMonthIndex)
        LazyColumn {
            itemsIndexed(filteredList) { position, _ ->
                ExpenseItem(
                    position,
                    filteredList,
                    onDelete = { list.removeAt(position) },
                    onEdit = {
                        editDialogOpen = true
                        editingIndex = position
                        editingName = list[position].name.toString()
                        editingValue = list[position].value.toString()
                        checked = list[position].installment
                        installmentsNumber = list[position].installments.toString()
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
                    value = editingValue,
                    onValueChange = { editingValue = it })
                Checkbox(checked = checked, onCheckedChange = {checked = it} )
                Text(text = "Parcelas: $installmentsNumber")
                Row() {
                    Button(
                        onClick = {
                            editingIndex?.let { index ->
                                list[index] = list[index].copy(
                                    name = editingName,
                                    value = editingValue.toDouble(),
                                    installment = checked
                                )
                            }
                            editDialogOpen = false
                            editingName = ""
                            editingValue = ""
                        }
                    ) {
                        Text(text = "Salvar")
                    }
                    Button(onClick = {
                        editDialogOpen = false
                        editingName = ""
                        editingValue = ""
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
        Log.d(TAG, "abrido")

        Dialog(
            onDismissRequest = { isDialogOpen = false }
        ) {
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp)
            ) {
                Text("Adicione um gasto")
                Text("O que é o gasto")
                OutlinedTextField(
                    value = editingName,
                    onValueChange = { editingName = it }
                )
                Text("Valor do gasto")
                OutlinedTextField(
                    value = editingValue,
                    onValueChange = { editingValue = it }
                )
                Text("Parcelado ou não")
                Checkbox(checked = checked, onCheckedChange = {checked = it} )
                if(checked == true) {
                    Log.d(TAG, "Abriu campo de qtd de parcelas")
                    Text("Quantidade de parcelas")
                    OutlinedTextField(value = installmentsNumber.toString(), onValueChange = {installmentsNumber = it})
                }
                Row() {
                    Button(onClick = {
                        if(editingValue.isNotEmpty() && editingName.isNotEmpty()) {
                            list.add(Expense(
                                name = editingName,
                                value = editingValue.toDouble(),
                                installment = checked,
                                installments = if (checked) installmentsNumber.toIntOrNull() ?: 1 else 1,
                                beginMonth = months[selectedMonthIndex]
                            ))
                            Log.v(TAG, "Lista atualizada: " + list)
                            isDialogOpen = false
                            editingName = ""
                            editingValue = ""
                            checked = false
                            installmentsNumber = ""
                        }
                    }) {
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