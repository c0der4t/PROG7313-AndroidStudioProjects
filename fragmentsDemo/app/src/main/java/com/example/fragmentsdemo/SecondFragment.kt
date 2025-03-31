package com.example.fragmentsdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
    val layout = inflater.inflate(R.layout.fragment_second, container, false)
        val txtInput: EditText = layout.findViewById(R.id.txtInput)
        val btnPressMe: Button = layout.findViewById(R.id.btnfrag2)
        val tvOutput: TextView = layout.findViewById(R.id.lblOutput)

        btnPressMe.setOnClickListener {
            tvOutput.text = txtInput.text
        }

        return layout
    }
}

