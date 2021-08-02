package com.dingshaoshuai.studycompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author: Xiao Bo
 * @date: 2/8/2021
 */

@Composable
fun Canvas1() {
    Canvas(modifier = Modifier.fillMaxSize()) {

    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xffff77
)
@Composable
fun PreviewCanvas() {

}