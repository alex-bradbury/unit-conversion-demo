package com.example.unit_conversion.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit

interface ConvertibleUnit {
    val units: Set<MeasureUnit>

    fun convertUnit(from: Measure, to: MeasureUnit): Double
}