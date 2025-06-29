package com.example.financecontrollapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.financecontrollapp.model.Expense

@Composable
fun ExpenseItem(
    position: Int,
    list: List<Expense>,
    onDelete:() -> Unit,
    onEdit:() -> Unit
){
    val name = list[position].name
    val value = list[position].value;
    val installment = list[position].installment;
    val installments = list[position].installments;

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(text = name.toString())
                Text(text = value.toString())
                Text(text = "Parcelado: " + installment.toString())
                Text(text = "Quantidade de parcelas: " + installments)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Row(){
                    IconButton(
                        onClick = onEdit
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ModeEdit,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Blue
                        )
                    }
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription =  null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}