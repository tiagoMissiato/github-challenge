package com.tiagomissiato.challenge.core.extensions

import java.lang.Math.abs
import java.text.DecimalFormat

fun Int.simplify(): String {
    var am = 0f
    val df = DecimalFormat("##.##")
    var simplify = ""
    if (abs(this/1000000000) >= 1) {
        am = this.toFloat() / 1000000000
        simplify = df.format(am) + "B"
    } else if (abs(this/1000000) >= 1) {
        am = this.toFloat() / 1000000
        simplify = df.format(am) + "M"
    } else if (abs(this/1000) >= 1) {
        am = this.toFloat() / 1000
        simplify = df.format(am) + "K"
    } else {
        simplify = this.toString()
    }

    return simplify
}