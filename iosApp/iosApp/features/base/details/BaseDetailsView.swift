//
//  BaseDetails.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BaseDetailsView: View {
    
    @ObservedObject var viewModel: BaseDetailsViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            MWDetailText(field: "Name", value: viewModel.asset.name)
            
            if viewModel.asset.symbol != "" {
                MWDetailText(field: "Symbol", value: viewModel.asset.symbol)
            }
            MWDetailText(field: "Amount", value: viewModel.asset.amount)
            
            MWDetailText(field: "Total", value: viewModel.asset.total)
            
            Spacer()
            Text("Transactions").bold().padding(.top)
            List {
                ForEach(viewModel.transactionItems, id: \.id) { transaction in
                    TransactionListItemView(item: transaction)
                }.onDelete { index in
                    viewModel.deleteTransaction(indexSet: index)
                }
            }.cornerRadius(25.0)
            
            HStack {
                Spacer()
                Text("Total: \(viewModel.asset.total)")
                    .font(.largeTitle)
                Spacer()
            }
            
            HStack(spacing: 20.0) {
                
                MWButton(text: "Edit") {
                    
                }
                
                MWButton(text: "Add Transactions") {
                    viewModel.addTransaction()
                }
            }
        }
        .padding()
        .navigationBarTitle(viewModel.asset.name, displayMode: .inline)
        .sheet(item: viewModel.transactionFormViewModel) { viewModelModel in
            TransactionFormView(viewModel: viewModelModel)
        }
    }
}

struct BaseDetails_Previews: PreviewProvider {
    static var previews: some View {
        let currency = CurrencyDto(
            id: "",
            name: "",
            symbol: ""
        )
        
        let asset = AssetDto(
            id: "1",
            name: "STAG Trans",
            code: "STAG",
            amount: 0.0,
            currency: currency,
            total: 10.0,
            transactions: [
                TransactionDto(id: "1", date: "10/10/2020", total: 10.00)
            ])
        
        NavigationView {
            BaseDetailsView(viewModel: BaseDetailsViewModel(asset: asset))
        }
    }
}
