package com.douglasqueiroz.mywallet.android.feature.addtransaction.logic

sealed class AddTransactionEvent() {
    object OnBack: AddTransactionEvent()
    object OnSave: AddTransactionEvent()
    class OnDateChange(val newValue: String): AddTransactionEvent()
    class OnPriceChange(val newValue: String): AddTransactionEvent()
    class OnQuantityChange(val newValue: String): AddTransactionEvent()
    class OnTotalChange(val newValue: String): AddTransactionEvent()
}
