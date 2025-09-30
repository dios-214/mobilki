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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

enum class Gender { MALE, FEMALE}

val ruslanDisplay = FontFamily(
    Font(R.font.rusdisplay)
)

val bowler = FontFamily(
    Font(R.font.bowler)
)
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

    fun getZodiac(day: Int, month: Int): Pair<String, Int> {
        return when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Овен" to R.drawable.oven
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Телец" to R.drawable.telec
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Близнецы" to R.drawable.blizneci
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Рак" to R.drawable.rak
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Лев" to R.drawable.lev
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Дева" to R.drawable.deva
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Весы" to R.drawable.vesi
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Скорпион" to R.drawable.scorpion
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Стрелец" to R.drawable.strelec
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Козерог" to R.drawable.kozerog
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Водолей" to R.drawable.vodoley
            (month == 2 && day >= 19) || (month == 3 && day <= 20) -> "Рыбы" to R.drawable.ribi
            else -> "Неизвестно" to R.drawable.unknown
        }
    }

    val datePicker = DatePickerDialog(
        context,
        { _, year, monthZeroBased, dayOfMonth ->
            birthYear = year
            birthMonth = monthZeroBased + 1
            birthDay = dayOfMonth
        },
        birthYear,
        birthMonth - 1,
        birthDay
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6992C7))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(24.dp),

                    ) {
                    Text(text = "РЕГИСТРАЦИЯ", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = ruslanDisplay)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("ФИО", fontFamily = bowler) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(4.dp)),
                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontFamily = bowler)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Пол",
                modifier = Modifier.padding(end = 12.dp),
                fontFamily = ruslanDisplay,
                color = Color.White,
                fontSize = 25.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == Gender.MALE,
                    onClick = { gender = Gender.MALE },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF454ed1),
                        unselectedColor = Color.White
                    )
                )
                Text(
                    "Мужчина",
                    modifier = Modifier.padding(end = 12.dp),
                    fontFamily = bowler,
                    color = Color.White
                )
                RadioButton(
                    selected = gender == Gender.FEMALE,
                    onClick = { gender = Gender.FEMALE },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF454ed1),
                        unselectedColor = Color.White
                    )
                )
                Text(
                    "Женщина",
                    modifier = Modifier.padding(end = 12.dp),
                    fontFamily = bowler,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Курс",
                modifier = Modifier.padding(end = 12.dp),
                fontFamily = ruslanDisplay,
                color = Color.White,
                fontSize = 25.sp)
            Box {
                OutlinedTextField(
                    value = selectedCourse,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(4.dp)),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black, fontFamily = bowler),
                    trailingIcon = {
                        IconButton(onClick = { courseExpanded = !courseExpanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = courseExpanded,
                    onDismissRequest = { courseExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    courses.forEach { c ->
                        DropdownMenuItem(
                            text = {
                                Text(text = c)
                            },
                            onClick = {
                                selectedCourse = c
                                courseExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val (diffLabel, diffPoints) = difficultyLabelAndPoints(difficulty)
            Text(
                "Сложность: $diffLabel (${diffPoints} баллов)",
                modifier = Modifier.padding(end = 12.dp),
                fontFamily = ruslanDisplay,
                color = Color.White,
                fontSize = 20.sp)
            Slider(
                value = difficulty,
                onValueChange = { difficulty = it },
                valueRange = 0f..2f,
                steps = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Дата \nрождения",
                        modifier = Modifier.padding(end = 12.dp),
                        fontFamily = ruslanDisplay,
                        color = Color.White,
                        fontSize = 25.sp)
                    Button(onClick = { datePicker.show() }) {
                        Text(
                            text = "${birthDay}.${birthMonth}.${birthYear}",
                            modifier = Modifier.padding(end = 12.dp),
                            fontFamily = bowler,
                            color = Color.White,
                            fontSize = 15.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))

                val (zodiacName, zodiacRes) = getZodiac(birthDay, birthMonth)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Знак \nзодиака",
                        modifier = Modifier.padding(end = 12.dp),
                        fontFamily = ruslanDisplay,
                        color = Color.White,
                        fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(4.dp))

                    Card(
                        modifier = Modifier.size(72.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF475d92)
                        )
                    ) {
                        Image(
                            painter = painterResource(id = zodiacRes),
                            contentDescription = zodiacName,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Text(text = zodiacName, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        if (fullName.isBlank()) {
                            Toast.makeText(context, "Введите ФИО", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val (label, points) = difficultyLabelAndPoints(difficulty)
                        val (zName, zRes) = getZodiac(birthDay, birthMonth)
                        val reg = RegistrationData(
                            fullName = fullName,
                            gender = gender,
                            course = selectedCourse,
                            difficultyLabel = label,
                            difficultyPoints = points,
                            birthDay = birthDay,
                            birthMonth = birthMonth,
                            birthYear = birthYear,
                            zodiacName = zName,
                            zodiacDrawableRes = zRes
                        )
                        registrations.add(reg)
                        Toast.makeText(context, "Данные сохранены", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF475d92)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(180.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Готово",
                        modifier = Modifier.padding(end = 15.dp),
                        fontFamily = bowler,
                        color = Color.White,
                        fontSize = 23.sp

                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))


            if (registrations.isNotEmpty()) {
                val last = registrations.last()
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Последняя регистрация:", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ФИО: ${last.fullName}")
                        Text("Пол: ${last.gender}")
                        Text("Курс: ${last.course}")
                        Text("Сложность: ${last.difficultyLabel} (${last.difficultyPoints} баллов)")
                        Text("Дата рождения: ${last.birthDay}.${last.birthMonth}.${last.birthYear}")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = last.zodiacDrawableRes),
                                contentDescription = last.zodiacName,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Знак: ${last.zodiacName}", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegistrationScreenPreview() {
    Lab2Theme {
        com.example.lab2.RegistrationScreen()
    }
}