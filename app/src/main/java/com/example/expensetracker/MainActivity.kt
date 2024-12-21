package com.example.expensetracker
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    private fun deleteExpenseFromDatabase(expenseNumber: String) {
        println("Expense $expenseNumber deleted.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account, auth) { isSuccess ->
                        if (isSuccess) {
                            Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Sign-in failed.", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        if (auth.currentUser != null) {
            setContent {
                ExpenseTrackerTheme {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "HomeScreen"
                    ) {
                        composable("HomeScreen") {
                            HomeScreen(navController)
                        }
                        composable("RecordExpenseScreen") {
                            RecordExpenseScreen(
                                navController = navController,
                                context = LocalContext.current,
                                onSave = { date, description, amount, isExpense -> }
                            )
                        }
                        composable("ExpenseDetailScreen") {
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
        } else {
            setContent {
                ExpenseTrackerTheme {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "SignInScreen"
                    ) {
                        composable("SignInScreen") {
                            SignInScreen(
                                onSignInSuccess = {
                                    navController.navigate("HomeScreen") {
                                        popUpTo("SignInScreen") { inclusive = true }
                                    }
                                },
                                googleSignInClient = googleSignInClient,
                                signInLauncher = signInLauncher
                            )
                        }
                    }
                }
            }
        }
    }
}

    @Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    googleSignInClient: GoogleSignInClient,
    signInLauncher: ActivityResultLauncher<Intent>
) {
    var isSigningIn by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to Expense Tracker", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isSigningIn = true
                    val signInIntent = googleSignInClient.signInIntent
                    signInLauncher.launch(signInIntent)
                },
                enabled = !isSigningIn
            ) {
                Text("Sign in with Google")
            }
        }
    }
}

private fun firebaseAuthWithGoogle(
    account: GoogleSignInAccount?,
    auth: FirebaseAuth,
    onSignInSuccess: (Boolean) -> Unit
) {
    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(true)
            } else {
                onSignInSuccess(false)
                println("Authentication failed: ${task.exception?.message}")
            }
        }
}

