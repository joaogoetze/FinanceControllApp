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
import com.example.financecontrollapp.model.Gasto

@Composable
fun GastoItem(
    position: Int,
    lista: List<Gasto>,
    onDelete:() -> Unit,
    onEdit:() -> Unit
){
    val nome = lista[position].nome
    val valor = lista[position].valor;
    val parcelado = lista[position].parcelado;
    val parcelas = lista[position].parcelas;

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
                Text(text = nome.toString())
                Text(text = valor.toString())
                Text(text = "Parcelado: " + parcelado.toString())
                Text(text = "Quantidade de parcelas: " + parcelas)
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