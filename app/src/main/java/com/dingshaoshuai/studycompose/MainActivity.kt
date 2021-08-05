package com.dingshaoshuai.studycompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.dingshaoshuai.studycompose.component.PreviewAnimation
import com.dingshaoshuai.studycompose.component.PreviewCanvas
import com.dingshaoshuai.studycompose.component.PreviewList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PreviewAnimation() }
    }
}

