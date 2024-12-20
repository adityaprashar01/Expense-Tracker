package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                color =  MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    NavHost(navController = navController , startDestination = "Screen_A",builder = {
                        composable("Screen_A",){
                            HomeScreen(navController)
                        }
                        composable("Screen_B",){
                            RecordExpenseScreen(
                                navController = navController,
                                context = LocalContext.current,
                                onSave = { date, description, amount, isExpense ->
                                } )
                        }

                    })
                }
            }
        }
    }
}


