package com.example.expensetracker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var transactions by remember { mutableStateOf(sampleTransactions.toMutableList()) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8))) {
            // Search bar
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search by title or amount",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
            Text(
                text = "Recent Transactions",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredTransactions = transactions.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            it.amount.toString().contains(searchQuery, ignoreCase = true)
                }
                items(filteredTransactions) { transaction ->
                    TransactionCard(transaction, onDelete = {
                        transactions.remove(transaction)
                    })
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxWidth().padding(50.dp)
            ) {
                Button(
                    onClick = { navController.navigate("Screen_B") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C3CCE))
                ) {
                    Text(
                        text = "+ Add New",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction, onDelete: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var showDeleteButton by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                        if (offsetX < -100) showDeleteButton = true else if (offsetX > -10) showDeleteButton = false
                    },
                    onDragEnd = {
                        if (offsetX < -100) offsetX = -100f else offsetX = 0f
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(text = transaction.subtitle, fontSize = 14.sp, color = Color.Gray)
                Text(text = transaction.date, fontSize = 12.sp, color = Color.Gray)
            }
            Text(text = "₹ ${transaction.amount}", fontSize = 16.sp, color = Color.Black)
        }
        if (showDeleteButton) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

// Sample Transaction Data
data class Transaction(val title: String, val subtitle: String, val date: String, val amount: Double)

val sampleTransactions = listOf(
    Transaction("Electricity Bill", "Expense #12", "15 Oct 2019", 1000.50),
    Transaction("Groceries", "Expense #11", "12 Oct 2019", 750.00),
    Transaction("Salary", "Income #1", "5 Oct 2019", 3000.50)
)
