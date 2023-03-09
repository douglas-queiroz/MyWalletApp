package com.douglasqueiroz.mywallet.android.feature.addtransaction.logic

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasqueiroz.mywallet.android.R
import com.douglasqueiroz.mywallet.android.extension.addMask
import com.douglasqueiroz.mywallet.android.extension.format
import com.douglasqueiroz.mywallet.android.extension.isDate
import com.douglasqueiroz.mywallet.android.extension.toFormattedString
import com.douglasqueiroz.mywallet.android.util.Constants.DATE_FORMAT_YYYY_MM_DD
import com.douglasqueiroz.mywallet.android.util.Constants.DATE_MASK_YYYY_MM_DD
import com.douglasqueiroz.mywallet.android.util.Constants.DATE_REGEX_YYYY_MM_DD
import com.douglasqueiroz.mywallet.android.util.StringResUtil
import com.douglasqueiroz.mywallet.domain.dto.AssetDto
import com.douglasqueiroz.mywallet.domain.usecases.AddTransactionUseCase
import com.douglasqueiroz.mywallet.domain.usecases.GetAssetUseCase
import kotlinx.coroutines.launch
import java.util.*

class AddTransactionViewModel(
    private val assetId: String,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val stringResUtil: StringResUtil,
    private val getAssetUseCase: GetAssetUseCase
): ViewModel() {

    var onBack: () -> Unit = {}

    var state: AddTransactionViewState by mutableStateOf(
        AddTransactionViewState(
            date = Date().toFormattedString().replace("-", "")
        )
    )
        private set

    private lateinit var assetDto: AssetDto

    init {
        loadAssetDto()
    }

    private fun loadAssetDto() = viewModelScope.launch {
        assetDto = getAssetUseCase.execute(assetId)!!
    }

    fun onEvent(event: AddTransactionEvent) {
        when(event) {
            is AddTransactionEvent.OnBack -> onBack()
            is AddTransactionEvent.OnSave -> addTransaction()
            is AddTransactionEvent.OnDateChange -> {

                val newValue = event.newValue

                if (newValue.length >= DATE_FORMAT_YYYY_MM_DD.length - 1) {
                    return
                }

                state = state.copy(date = newValue, dateErrorMsg = null)
            }
            is AddTransactionEvent.OnPriceChange -> {
                val quantity = state.quantity.toDoubleOrNull()
                val price = event.newValue.toDoubleOrNull()

                state = if (quantity != null && price != null) {
                    val total = quantity * price

                    state.copy(
                        price = event.newValue,
                        priceErrorMsg = null,
                        total = total.format()
                    )
                } else {
                    state.copy(price = event.newValue, priceErrorMsg = null)
                }
            }
            is AddTransactionEvent.OnQuantityChange -> {
                val quantity = event.newValue.toDoubleOrNull()
                val price = state.price.toDoubleOrNull()

                state = if (price != null && quantity != null) {
                    val total = quantity * price

                    state.copy(
                        quantity = event.newValue,
                        quantityErrorMsg = null,
                        total = total.format(),
                        totalErrorMsg = null
                    )
                } else {
                    state.copy(quantity = event.newValue, quantityErrorMsg = null)
                }
            }
            is AddTransactionEvent.OnTotalChange -> {
                val total = event.newValue.toDoubleOrNull()
                val quantity = state.quantity.toDoubleOrNull()

                state = if (total != null && quantity != null) {
                    val price = total/quantity

                    state.copy(
                        total = event.newValue,
                        totalErrorMsg = null,
                        price = price.format(),
                        priceErrorMsg = null
                    )
                } else {
                    state.copy(total = event.newValue, totalErrorMsg = null)
                }
            }
        }
    }

    private fun addTransaction() = viewModelScope.launch {

        var isValid = true

        val quantity = state.quantity.toFloatOrNull()

        if (quantity == null) {

            state = state.copy(
                quantityErrorMsg = stringResUtil.getString(R.string.add_transaction_invalid_quantity)
            )

            isValid = false
        }

        val price = state.price.toFloatOrNull()

        if (price == null) {

            state = state.copy(
                priceErrorMsg = stringResUtil.getString(R.string.add_transaction_invalid_price)
            )

            isValid = false
        }

        val date = state.date.addMask(DATE_MASK_YYYY_MM_DD)

        if (!date.isDate()) {

            state = state.copy(
                dateErrorMsg = stringResUtil.getString(R.string.add_transaction_invalid_date)
            )

            isValid = false
        }

        if (!isValid) {
            return@launch
        }

        addTransactionUseCase.execute(
            asset = assetDto,
            date = date,
            quantity = quantity!!,
            price = price!!
        )

        onBack()

    }
}