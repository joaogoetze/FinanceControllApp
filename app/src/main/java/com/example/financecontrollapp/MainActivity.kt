package com.example.financecontrollapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        Log.i(TAG, "Vai aparecer oua alguém vai morrer")
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    companion object {
        private val TAG = "Teste"
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(){

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Liberdade Financeira", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF206D00)
                )
            )
        }
    ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ){
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable(route="home") {
                        Home(navController = navController)
                    }
                    composable(route = "finaceControll") {
                        FinanceControll()
                    }
                }
            }
    }
}

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment =  Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { navController.navigate("finaceControll") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF206D00))
        ) {
            Text(text = "Controle Financeiro")
        }
    }
}