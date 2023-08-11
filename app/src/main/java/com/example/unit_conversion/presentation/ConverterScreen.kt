package com.example.unit_conversion.presentation

import android.icu.util.MeasureUnit
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit_conversion.data.ConvertibleUnitType
import com.example.unit_conversion.ui.theme.UnitconversionTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Converter(
    viewModel: ConverterViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Text(
                text = "Unit Converter",
                fontSize = TextUnit(9f, TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            ConvertibleUnitType.values().forEach {
                println(it)
                IconButton(
                    selected = it == uiState.currentUnitType,
                    unitType = it,
                    onClick = viewModel::setUnitType
                )
            }
        }
        Row(
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 10.dp),
                label = { Text("Input") },
                supportingText = { uiState.inputError?.let { Text(text = it) } },
                isError = uiState.inputError != null,
                value = uiState.input,
                onValueChange = { viewModel.updateInput(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            UnitsDropdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                errorText = uiState.unitFromSelectionError,
                expanded = uiState.unitFromPickerExpanded,
                selected = uiState.unitFromSelection?.subtype ?: "",
                onExpandedChange = viewModel::unitFromPickerChange,
                onItemSelected = viewModel::setUnitFromSelection,
                items = uiState.currentUnitType.converter.units
            )
        }
        Row(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 10.dp),
                label = { Text("Output") },
                value = uiState.output,
                onValueChange = {},
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
            )
            UnitsDropdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                errorText = uiState.unitToSelectionError,
                expanded = uiState.unitToPickerExpanded,
                selected = uiState.unitToSelection?.subtype ?: "",
                onExpandedChange = viewModel::unitToPickerChange,
                onItemSelected = viewModel::setUnitToSelection,
                items = uiState.currentUnitType.converter.units
            )
        }
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                viewModel.convertUnits()
            }
        ) {
            Text(text = "Convert")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnitsDropdown(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    expanded: Boolean,
    selected: String,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (MeasureUnit) -> Unit,
    items: Set<MeasureUnit>
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { TrailingIcon(expanded = expanded) },
            supportingText = { errorText?.let { Text(text = it) } },
            isError = errorText != null,
            modifier = Modifier.menuAnchor(),
            label = { Text("Units") }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            items.forEach {
                println(it)
                DropdownMenuItem(
                    text = { Text(it.subtype) },
                    onClick = { onItemSelected(it) }
                )
            }
        }
    }
}

@Composable
private fun IconButton(
    selected: Boolean,
    unitType: ConvertibleUnitType,
    onClick: (ConvertibleUnitType) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick(unitType) }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = unitType.icon,
            contentDescription = unitType.unitName,
            tint = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
        Text(
            text = unitType.unitName,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun Preview() {
    UnitconversionTheme {
        Converter()
    }
}