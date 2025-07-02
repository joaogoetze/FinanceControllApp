package com.example.financecontrollapp

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Months(selectedMonth: String, onMonthSelected: (String) -> Unit, selectedMonthIndex: Int) {

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue

    val TAG = "EAE"

    val months = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )


    val reorderedMonths = months.subList(currentMonth - 1, months.size) + months.subList(0, currentMonth - 1)

    LaunchedEffect(Unit) {
        onMonthSelected(reorderedMonths.first())
    }

    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(reorderedMonths) { month ->
            Log.d(TAG, "mes: " + month)
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(35.dp)
                    .padding(5.dp)
                    .clickable { onMonthSelected(month) },
                colors = CardDefaults.cardColors(
                    contentColor = if (selectedMonth == month) Color.Gray else Color.White
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = month)
                }
            }
        }
    }
}