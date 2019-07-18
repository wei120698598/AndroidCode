package com.wei.sample.ue

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.backgroundColor

class UEActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scalpelFrameLayout = ScalpelFrameLayout(this)
        scalpelFrameLayout.setDrawIds(true)
        scalpelFrameLayout.setDrawViews(true)
        scalpelFrameLayout.isLayerInteractionEnabled = true
        scalpelFrameLayout.layoutParams = ViewGroup.LayoutParams(-1, -1)

        val linearLayout = LinearLayout(this)
        linearLayout.backgroundColor = Color.GRAY

        val textView = TextView(this)
        textView.text = "哈哈哈哈哈"
        textView.gravity = Gravity.CENTER
        textView.backgroundColor = Color.GREEN

        textView.setOnClickListener {
            Toast.makeText(this@UEActivity, "哈哈", Toast.LENGTH_LONG).show()
        }

        linearLayout.addView(textView, -2, -2)

        scalpelFrameLayout.addView(linearLayout, -1, -1)

        setContentView(scalpelFrameLayout)
    }
}