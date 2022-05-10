//
//  TransactionFormView.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TransactionFormView: View {
    
    @StateObject var viewModel: TransactionFormViewModel
    
    var body: some View {
        Form {
            MWTextField(
                field: "Date (yyyy-mm-dd)",
                keyboardType: .numberPad,
                value: $viewModel.date
            )
            MWTextField(
                field: "Amount",
                keyboardType: .decimalPad,
                value: $viewModel.amount
            )
            MWTextField(
                field: "Price",
                keyboardType: .numbersAndPunctuation,
                value: $viewModel.price
            )
            
            MWButton(text: "Save") {
                viewModel.onSaveBtnClick()
            }
        }
    }
}

struct TransactionFormView_Previews: PreviewProvider {
    static var previews: some View {
        let currencyDto = CurrencyDto(id: "", name: "", symbol: "")
        let activeDto = AssetDto(id: "''", name: "", code: "", currency: currencyDto, total: 10.0, transactions: [TransactionDto]())
        TransactionFormView(
            viewModel: TransactionFormViewModel(
                active: activeDto,
                state: .init(),
                onCompleted: {}
            )
        )
    }
}
