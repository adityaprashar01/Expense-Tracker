//package com.example.expensetracker
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//
//data class ExpenseDetail(
//    val expenseNumber: String,
//    val date: String,
//    val description: String,
//    val amount: Double
//)
//@Composable
//fun ExpenseDetailScreen(
//    navController: NavController,
//    expense: ExpenseDetail,
//    onNavigateBack: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Scaffold { paddingValues ->
//        Column(
//            modifier = modifier
//
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp)
//                .background(Color.White)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .padding(8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = onNavigateBack) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Text(
//                    text = "Expense",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.weight(1f)
//                )
//
//                // Edit and Delete icons
//                IconButton(onClick = { /* Handle edit */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = "Edit expense"
//                    )
//                }
//
//                IconButton(onClick = { /* Handle delete */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete expense"
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Expense #${expense.expenseNumber}",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = expense.date,
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Description",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = expense.description,
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Total Amount",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "₹ ${expense.amount}",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//
//
package com.example.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

data class ExpenseDetail(
    val expenseNumber: String,
    val date: String,
    val description: String,
    val amount: Double
)

@Composable
fun ExpenseDetailScreen(
    navController: NavController,
    expense: ExpenseDetail,
    onNavigateBack: () -> Unit,
    onEditExpense: (ExpenseDetail) -> Unit,
    onDeleteExpense: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black


                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Expense",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    color = Color.Black
                )
                // Edit Button
                IconButton(onClick = {
                    onEditExpense(expense)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit expense",
//                        tint = MaterialTheme.colorScheme.primary,
                        tint = Color.Black,
                    )
                }

                // Delete Button
                IconButton(onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Expense deleted",
                            actionLabel = "Undo"
                        )
                    }
                    onDeleteExpense(expense.expenseNumber)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete expense",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expense Number
            Text(
                text = "Expense #${expense.expenseNumber}",
                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.primary,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Date: ${expense.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = expense.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "₹ ${expense.amount}",
                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}
