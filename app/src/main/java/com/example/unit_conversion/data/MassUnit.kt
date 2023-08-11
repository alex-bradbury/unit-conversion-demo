package com.example.unit_conversion.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.POUND
import android.icu.util.MeasureUnit.GRAM
import android.icu.util.MeasureUnit.TON

class MassUnit: ConvertibleUnit {
    override val units: Set<MeasureUnit>
        get() = setOf(
            POUND,
            GRAM,
            TON
        )

    override fun convertUnit(from: Measure, to: MeasureUnit): Double {
        if (!units.contains(from.unit) || !units.contains(to)) {
            return 0.0
        }
        val result = from.number.toDouble()

        when(from.unit) {
            POUND -> when(to) {
                POUND -> return result
                GRAM -> return result*453.59
                TON -> return result/2000
            }
            GRAM -> when(to) {
                POUND -> return result/453.59
                GRAM -> return result
                TON -> return result/907200
            }
            TON -> when(to) {
                POUND -> return result*2000
                GRAM -> return result*907200
                TON -> return result
            }
        }

        return result
    }
}