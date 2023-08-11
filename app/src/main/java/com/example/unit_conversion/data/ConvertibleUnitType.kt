package com.example.unit_conversion.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.ui.graphics.vector.ImageVector

enum class ConvertibleUnitType(
    val unitName: String,
    val icon: ImageVector,
    val converter: ConvertibleUnit
) {
    LENGTH(
        "Length",
        Icons.Default.Straighten,
        LengthUnit()
    ),
    MASS(
        "Mass",
        Icons.Default.Scale,
        MassUnit()
    )
}