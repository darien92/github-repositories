package com.darien.search_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.darien.core_ui.util.SharedResources

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    isSingleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    keyboardAction: KeyboardActions = KeyboardActions.Default,
    trailingIcon: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SharedResources.SharedDims.margin_md)
    ) {
        var textFieldValueState by remember {
            mutableStateOf(
                TextFieldValue(
                    text = value
                )
            )
        }
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = textFieldValueState,
            onValueChange = { inputValue ->
                textFieldValueState = inputValue
                onValueChange(inputValue.text)
            },
            label = {
                Text(text = label)
            },
            singleLine = isSingleLine,
            textStyle = MaterialTheme.typography.body1,
            enabled = enabled,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = keyboardAction,
            trailingIcon = {
                trailingIcon?.invoke()
            }
        )
    }
}