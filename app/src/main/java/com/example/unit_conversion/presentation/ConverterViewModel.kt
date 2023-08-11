package com.example.unit_conversion.presentation

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.unit_conversion.data.ConvertibleUnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConverterViewModel : ViewModel() {
    private var _state = MutableStateFlow(ConverterState())
    val state: StateFlow<ConverterState> = _state.asStateFlow()

    fun updateInput(input: String) {
        _state.value = _state.value.copy(
            input = input,
            inputError = null
        )
    }

    fun unitToPickerChange(state: Boolean) {
        _state.value = _state.value.copy(
            unitToPickerExpanded = state,
            unitToSelectionError = null
        )
    }

    fun unitFromPickerChange(state: Boolean) {
        _state.value = _state.value.copy(
            unitFromPickerExpanded = state,
            unitFromSelectionError = null
        )
    }

    fun setUnitToSelection(unit: MeasureUnit) {
        _state.value = _state.value.copy(unitToSelection = unit)
        unitToPickerChange(false)
    }

    fun setUnitFromSelection(unit: MeasureUnit) {
        _state.value = _state.value.copy(unitFromSelection = unit)
        unitFromPickerChange(false)
    }

    fun setUnitType(unitType: ConvertibleUnitType) {
        _state.value = _state.value.copy(
            currentUnitType = unitType,
            unitFromSelectionError = null,
            unitFromSelection = null,
            unitToSelectionError = null,
            unitToSelection = null,
            output = ""
        )
    }

    fun convertUnits() {
        val state = state.value
        var errors = false
        if (state.unitToSelection == null) {
            _state.value = _state.value.copy(unitToSelectionError = "Select a unit to convert to!")
            errors = true
        }
        if (state.unitFromSelection == null) {
            _state.value =
                _state.value.copy(unitFromSelectionError = "Select a unit to convert from!")
            errors = true
        }

        if (state.input.isEmpty()) {
            _state.value = _state.value.copy(inputError = "Provide an input!")
            errors = true
        }
        if (!state.input.isDigitsOnly()) {
            _state.value = _state.value.copy(inputError = "Provide a numeric input!")
            errors = true
        }

        if (!errors) {
            val outputDouble = state.currentUnitType.converter.convertUnit(
                from = Measure(state.input.toDouble(), state.unitFromSelection),
                to = state.unitToSelection!!
            )

            _state.value = _state.value.copy(output = outputDouble.toString())
        }
    }
}