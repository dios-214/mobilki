package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab2.ui.theme.Lab2Theme
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

enum class Gender { MALE, FEMALE}

data class RegistrationData(
    val fullName: String,
    val gender: com.example.lab2.Gender,
    val course: String,
    val difficultyLabel: String,
    val difficultyPoints: Int,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val zodiacName: String,
    val zodiacDrawableRes: Int
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf(com.example.lab2.Gender.MALE) }
    val courses = listOf("1 курс", "2 курс", "3 курс", "4 курс")
    var selectedCourse by rememberSaveable { mutableStateOf(courses[0]) }
    var courseExpanded by remember { mutableStateOf(false) }

    var difficulty by rememberSaveable { mutableStateOf(1f) }
    fun difficultyLabelAndPoints(value: Float): Pair<String, Int> {
        return when (value.toInt()) {
            0 -> "Легкое" to 2
            1 -> "Среднее" to 4
            else -> "Сложное" to 6
        }
    }


    val calendar = Calendar.getInstance()
    var birthDay by rememberSaveable { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
    var birthMonth by rememberSaveable { mutableStateOf(calendar.get(Calendar.MONTH) + 1) }
    var birthYear by rememberSaveable { mutableStateOf(calendar.get(Calendar.YEAR)) }


    val registrations = remember { mutableStateListOf<com.example.lab2.RegistrationData>() }
    val context = LocalContext.current



}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegistrationScreenPreview() {
    Lab2Theme {
        com.example.lab2.RegistrationScreen()
    }
}