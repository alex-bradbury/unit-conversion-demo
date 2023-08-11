package com.example.unit_conversion.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.METER
import android.icu.util.MeasureUnit.FOOT
import android.icu.util.MeasureUnit.MILE

class LengthUnit: ConvertibleUnit {
    override val units: Set<MeasureUnit>
        get() = setOf(
            METER,
            FOOT,
            MILE
        )

    override fun convertUnit(from: Measure, to: MeasureUnit): Double {
        if (!units.contains(from.unit) || !units.contains(to)) {
            return 0.0
        }
        val result = from.number.toDouble()

        when(from.unit) {
            METER -> when(to) {
                METER -> return result
                FOOT -> return result*3.281
                MILE -> return result/1609
            }
            FOOT -> when(to) {
                METER -> return result/3.281
                FOOT -> return result
                MILE -> return result/5280
            }
            MILE -> when(to) {
                METER -> return result*1609
                FOOT -> return result*5280
                MILE -> return result
            }
        }

        return result
    }
}