package com.example.expensetracker
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun RecordExpenseScreen(
    navController: NavController,
    context: Context,
    onSave: (String, String, Double, Boolean) -> Unit
) {
    var isExpense by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Record Expense",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ToggleButton(
                    text = "Expense",
                    selected = isExpense,
                    onClick = { isExpense = true },
                    modifier = Modifier.weight(1f)
                )
                ToggleButton(
                    text = "Income",
                    selected = !isExpense,
                    onClick = { isExpense = false },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("DATE")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("*")
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                ) {
                    BasicTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 0.dp)
                            .align(Alignment.CenterStart),
                        decorationBox = { innerTextField ->
                            if (selectedDate.isEmpty()) {
                                Text(
                                    text = "Select Date",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )

                    IconButton(
                        onClick = {
                            showDatePicker(context) { date ->
                                selectedDate = date
                            }
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterEnd)
                            .padding(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Select Date",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("DESCRIPTION ")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("*")
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                ){
                    BasicTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 0.dp)
                            .align(Alignment.CenterStart),
                        decorationBox = { innerTextField ->
                            if (description.isEmpty()) {
                                Text(
                                    text = "Enter Description",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Total Amount ")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("*")
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                ) {
                    Text(
                        text = "₹",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart)
                    )

                    BasicTextField(
                        value = amount,
                        onValueChange = {
                            if (it.matches("^[0-9]*\\.?[0-9]{0,2}\$".toRegex())) {
                                amount = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp)
                            .align(Alignment.CenterStart),
                        decorationBox = { innerTextField ->
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF4C3CCE),
                shape = RoundedCornerShape(8.dp)
            ) {
                Button(
                    onClick = {
                        val parsedAmount = amount.toDoubleOrNull() ?: 0.0
                        onSave(selectedDate, description, parsedAmount, isExpense)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun ToggleButton(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = if (selected) Color(0xFF4C3CCE) else Color.Black
        ),
        border = if (selected) {
            BorderStroke(2.dp, Color(0xFF4C3CCE))
        } else {
            BorderStroke(1.dp, Color.Gray)
        },
        modifier = modifier.padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(50),
                color = if (selected) Color(0xFF4C3CCE) else Color.Gray,
                modifier = Modifier.size(12.dp)
            ) {}
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text)
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected("$selectedDay ${getMonthName(selectedMonth)} $selectedYear")
        },
        year,
        month,
        day
    ).show()
}

fun getMonthName(month: Int): String {
    return arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )[month]
}

