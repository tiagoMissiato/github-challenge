package com.tiagomissiato.challenge.repository.presenter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.tiagomissiato.challenge.repository.R
import com.tiagomissiato.challenge.repository.databinding.CustomViewGitInfoBinding

class GitInfoCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding: CustomViewGitInfoBinding
    private var viewType: Int = 0

    var text: String = ""
        set(value) {
            field = value
            binding.customText.text = value
        }

    init {

        binding = CustomViewGitInfoBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GitInfoCustomView,
            0, 0
        ).apply {

            try {
                viewType = getInt(R.styleable.GitInfoCustomView_type, 0)
                when (viewType) {
                    FORK -> {
                        binding.icon.setImageResource(R.drawable.fork)
                    }
                    LICENSE -> {
                        binding.icon.setImageResource(R.drawable.license)
                    }
                    else -> {
                        binding.icon.setImageResource(R.drawable.star)
                    }
                }
            } finally {
                recycle()
            }

        }
    }

    companion object Type {
        const val STAR = 0
        const val FORK = 1
        const val LICENSE = 2

    }
}