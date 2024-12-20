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
    private fun deleteExpenseFromDatabase(expenseNumber: String) {
        println("Expense $expenseNumber deleted.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "Screen_A"
                    ) {
                        composable("Screen_A") {
                            HomeScreen(navController)
                        }
                        composable("Screen_B") {
                            RecordExpenseScreen(
                                navController = navController,
                                context = LocalContext.current,
                                onSave = { date, description, amount, isExpense ->
                                }
                            )
                        }
                        composable("Screen_C") {
                            val expense = ExpenseDetail(
                                expenseNumber = "001",
                                date = "15 Oct 2024",
                                description = "Electricity Bill Payment",
                                amount = 1500.0
                            )
                            ExpenseDetailScreen(
                                onNavigateBack = { navController.popBackStack() },
                                navController = navController,
                                expense = expense,
                                modifier = Modifier.fillMaxSize(),
                                onEditExpense = { exp ->
                                    navController.navigate("EditExpenseScreen/${exp.expenseNumber}")
                                },
                                onDeleteExpense = { expenseNumber ->
                                    deleteExpenseFromDatabase(expenseNumber)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
