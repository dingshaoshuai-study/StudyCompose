package com.dingshaoshuai.studycompose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * @author: Xiao Bo
 * @date: 2/8/2021
 */

@Composable
fun Layout1(infoClick: () -> Unit) {

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            // 默认 Start 开始位置
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                // 如果指定的尺寸不符合来自布局父项的约束条件，则可能不会采用该尺寸(什么叫不符合，还不知道)
//             .size(width = 300.dp, height = 150.dp)
                .requiredSize(width = 300.dp, height = 150.dp)
                .background(color = Color.Blue)
        ) {
            Text(
                text = "赵本山",
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Green)
            )
            Column(
                Modifier
                    // 代码顺序很重要 ↓
                    // padding 修饰在 clickable，padding 位置也是可以点击的
                    .clickable(onClick = infoClick)
                    .background(color = Color.Red)
                    .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
                    // 填充父项提供的最大宽度
                    .fillMaxWidth()
            ) {
                Text(text = "马大帅")
                Text(text = "我是一个传奇人物")
            }

        }
    }
}

@Composable
fun Layout2() {
    Row(Modifier.background(color = Color.Blue)) {
        Text(text = "你好", modifier = Modifier.weight(1f))
        Text(text = "Android", modifier = Modifier.weight(1f))
    }
}

@Composable
fun Layout3ConstraintLayout() {
    ConstraintLayout {
        val (btn, tv) = createRefs()

        Button(onClick = { },
            modifier = Modifier.constrainAs(btn) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(parent.start, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
            }) {
            Text(text = "Button")
        }

        Text(text = "Text", Modifier.constrainAs(tv) {
            top.linkTo(btn.bottom, margin = 10.dp)
            start.linkTo(btn.start)
            end.linkTo(btn.end)
        })
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xffff77
)
@Composable
fun PreviewLayout() {
    Layout3ConstraintLayout()
}
