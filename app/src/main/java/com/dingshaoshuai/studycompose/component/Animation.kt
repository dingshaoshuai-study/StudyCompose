package com.dingshaoshuai.studycompose.component

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @author: Xiao Bo
 * @date: 3/8/2021
 */

/**
 * 修改颜色动画
 */
@Composable
fun Animation1() {
    var blue by remember { mutableStateOf(true) }
//    val color = if (blue) Color.Blue else Color.Red
    val color by animateColorAsState(targetValue = if (blue) Color.Blue else Color.Red)

    Column {
        Button(onClick = { blue = !blue }) {
            Text(text = "点我看效果")
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color)
        )
    }
}

private enum class BoxSate {
    Small,
    Large
}

/**
 * 修改大小和颜色的动画
 */
@Composable
fun Animation2() {
    var boxState by remember { mutableStateOf(BoxSate.Small) }
    val transition = updateTransition(targetState = boxState, label = "")
    val color by transition.animateColor(label = "") {
        when (it) {
            BoxSate.Small -> Color.Blue
            BoxSate.Large -> Color.Red
        }
    }
    val size by transition.animateDp(label = "") {
        when (it) {
            BoxSate.Small -> 100.dp
            BoxSate.Large -> 200.dp
        }
    }

    Column {
        Button(onClick = {
            boxState = when (boxState) {
                BoxSate.Small -> BoxSate.Large
                BoxSate.Large -> BoxSate.Small
            }
        }) {
            Text(text = "点我就变大")
        }
        Box(
            modifier = Modifier
                .size(size = size)
                .background(color = color)
        )
    }
}

/**
 * 显示和隐藏的动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Animation3() {
    var visible by remember { mutableStateOf(true) }

    Column {
        Button(onClick = { visible = !visible }) {
            Text(text = "点我看效果")
        }
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
    }
}

/**
 * 文字展开和收起的动画
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Animation4() {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { expanded = !expanded }) {
            Text(text = "点我看效果")
        }
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .animateContentSize()
        ) {
            Text(
                text = "我是一段很长的文字".repeat(50),
                maxLines = if (expanded) Int.MAX_VALUE else 2
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewAnimation() {
    Animation4()
}