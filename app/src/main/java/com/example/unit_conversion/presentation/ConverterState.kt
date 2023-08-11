package com.example.unit_conversion.presentation

import android.icu.util.MeasureUnit
import com.example.unit_conversion.data.ConvertibleUnitType

data class ConverterState(
    var input: String = "",
    var inputError: String? = null,
    var output: String = "",
    var currentUnitType: ConvertibleUnitType = ConvertibleUnitType.LENGTH,
    var unitFromPickerExpanded: Boolean = false,
    var unitFromSelection: MeasureUnit? = null,
    var unitFromSelectionError: String? = null,
    var unitToPickerExpanded: Boolean = false,
    var unitToSelection: MeasureUnit? = null,
    var unitToSelectionError: String? = null
)
