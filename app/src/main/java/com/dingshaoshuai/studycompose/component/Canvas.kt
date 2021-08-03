package com.dingshaoshuai.studycompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author: Xiao Bo
 * @date: 2/8/2021
 */

@Composable
fun Canvas1() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // 画线
        drawLine(
            color = Color.Blue,
            start = Offset(x = canvasWidth, y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            strokeWidth = 5f
        )

        // 画圆
        drawCircle(
            color = Color.Red,
            radius = 100f,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
        )

        // 画矩形
        drawRect(
            color = Color.Blue,
            size = Size(200f, 100f)
        )

        // 画圆角矩形
        drawRoundRect(
            color = Color.Green,
            size = Size(200f, 100f),
            topLeft = Offset(0f, 120f),
            cornerRadius = CornerRadius(20f, 20f)
        )

        // 移动画布
        inset(50f, 50f) {
            // 重载了 div 方法，所以 / 2F 可以获得 Size 对象
            drawRect(
                color = Color.Green,
                size = size / 2F
            )
        }

        // 旋转画布 45°
        rotate(45f) {
            // 重载了 div 方法，所以 / 2F 可以获得 Size 对象
            drawRect(
                color = Color.Red,
                size = size / 2F
            )
        }
    }
}

@Composable
fun Canvas2() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        withTransform({
            // 向 left、top 各偏移 100px
            translate(left = 100f, top = 100f)
//            rotate(45f)
        }, {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                color = Color.Black,
                start = Offset(canvasWidth / 2f, 0f),
                end = Offset(canvasWidth / 2f, canvasHeight),
            )
        })
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewCanvas() {
    Canvas2()
}