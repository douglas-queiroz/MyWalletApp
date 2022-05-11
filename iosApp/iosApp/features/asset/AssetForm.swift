//
//  AssetForm.swift
//  iosApp
//
//  Created by Douglas Queiroz on 02/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AssetForm: View {

    let currencies = ["Euro", "Real", "Dolar"]
    
    @State var currency = ""
    @ObservedObject var viewModel: AssetFormViewModel
    
    var body: some View {
        NavigationView {
            Form {
                MWTextField(
                    field: "Name",
                    value: $viewModel.name
                )
                
                MWTextField(
                    field: "Symbol",
                    value: $viewModel.symbol
                )
                
                Picker("Currency", selection: $viewModel.selectedCurrency) {
                    ForEach(viewModel.currencyList, id: \.id) { currency in
                        Text(currency.name)
                    }
                }.pickerStyle(.inline)
                
                MWButton(text: "Save") {
                    viewModel.onSaveClick()
                }
                
            }.navigationTitle("Asset")
        }
    }
}

struct AssetForm_Previews: PreviewProvider {
    static var previews: some View {
        AssetForm(viewModel: AssetFormViewModel())
    }
}
