/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

// Start building your app here!

@ExperimentalAnimationApi
@Composable
fun StarIcon(isAnimated: Boolean, animationFrequency: Int, visibility: Boolean) {

    var baseModifier = Modifier
        .width(40.dp)
        .height(40.dp)
        .size(80.dp, 80.dp)

    if (isAnimated) {
        val infiniteTransition = rememberInfiniteTransition()
        val animationNumber = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationFrequency
                )
            )
        )

        if (animationNumber.value <= .5f) {
            baseModifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .size(80.dp, 80.dp)
        }
    }

    AnimatedVisibility(visible = visibility) {
        Image(
            painter = painterResource(id = R.drawable.ic_sparkles),
            contentDescription = "sparkling stars icon",
            modifier = baseModifier
        )
    }
}

@Composable
fun Header() {
    Row {
        Text(
            modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 24.dp)),
            text = "Star countdown timer",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CountdownText(visible: Boolean, timervalue: Int) {

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
            initialAlpha = 0.4f
        ),
        exit = fadeOut(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        Text(
            modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 24.dp)),
            text = "$timervalue",
            color = MaterialTheme.colors.onSurface,
            style = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontSize = 100.sp,
                fontFamily = FontFamily(Font(R.font.gv_time_regular, FontWeight.Light)),
            )
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CountdownButton(visible: Boolean, onButtonClick: () -> Unit, timer: CountDownTimer) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
            initialAlpha = 0.4f
        ),
        exit = fadeOut(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        Button(
            onClick = {
                onButtonClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
            modifier = Modifier.padding(PaddingValues(top = 16.dp))
        ) {
            Text(
                "Take off",
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 34.sp,
                    fontFamily = FontFamily(Font(R.font.gv_time_regular, FontWeight.Light)),
                ),
            )
        }
    }
}

@Composable
fun PlanetImage(modifier: Modifier, isAnimationLaunched: Boolean) {
    var colorFilterValue: ColorFilter? = ColorFilter.tint(Color.Gray)

    if (isAnimationLaunched) {
        val infiniteTransition = rememberInfiniteTransition()
        val animationNumber = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400
                )
            )
        )

        if (animationNumber.value <= .5f) {
            colorFilterValue = null
        }
    }

    Image(
        painter = painterResource(id = R.drawable.ic_planet),
        contentDescription = "a beautiful planet image",
        modifier = modifier,
        colorFilter = colorFilterValue
    )
}

@ExperimentalAnimationApi
@Composable
fun Body(
    starIconAnimation: Boolean,
    starIconVisibility: Boolean,
    countdownTextVisible: Boolean,
    timerValue: Int
) {
    Spacer(modifier = Modifier.size(50.dp))

    StarIcon(isAnimated = starIconAnimation, 721, starIconVisibility)
    Spacer(modifier = Modifier.size(50.dp))
    Row {
        StarIcon(isAnimated = starIconAnimation, 419, starIconVisibility)
        CountdownText(countdownTextVisible, timerValue)
        Row {
            StarIcon(isAnimated = starIconAnimation, 621, starIconVisibility)
        }
    }
    Spacer(modifier = Modifier.size(50.dp))
    StarIcon(isAnimated = starIconAnimation, 812, starIconVisibility)
}

@ExperimentalAnimationApi
@Composable
fun MyApp() {
    MyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val countdownTime: Long = 10000
            var timerValue by remember { mutableStateOf((countdownTime / 1000).toInt()) }
            var countdownTextVisible by remember { mutableStateOf(false) }
            var countdownButtonVisible by remember { mutableStateOf(true) }
            var starIconAnimation by remember { mutableStateOf(false) }
            var starIconVisibility by remember { mutableStateOf(false) }
            val timer = object : CountDownTimer(countdownTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerValue--
                }

                override fun onFinish() {
                    timerValue = (countdownTime / 1000).toInt()
                    countdownButtonVisible = true
                    countdownTextVisible = false
                    starIconAnimation = false
                    starIconVisibility = false
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Header()
                Spacer(
                    modifier = Modifier
                        .size(16.dp)
                )

                PlanetImage(
                    Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    countdownTextVisible
                )

                CountdownButton(
                    countdownButtonVisible,
                    onButtonClick = {
                        countdownButtonVisible = false
                        Handler().postDelayed(
                            {
                                countdownTextVisible = true
                                timer.start()
                                starIconVisibility = true
                                starIconAnimation = true
                            },
                            1000
                        )
                    },
                    timer
                )
                Body(starIconAnimation, starIconVisibility, countdownTextVisible, timerValue)
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun DarkPreview() {
    MyTheme {
        MyApp()
    }
}
