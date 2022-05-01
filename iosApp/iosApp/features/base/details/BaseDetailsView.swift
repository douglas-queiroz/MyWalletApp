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
    
    let viewModel: BaseDetailsViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            MWDetailText(field: "Name", value: viewModel.active.name)
            
            if viewModel.active.symbol != "" {
                MWDetailText(field: "Symbol", value: viewModel.active.symbol)
            }
            
            MWDetailText(field: "Total", value: viewModel.active.total)
            
            Spacer()
            Text("Transactions").bold().padding(.top)
            List(viewModel.transactionItems) {
                TransactionListItemView(item: $0)
            }.cornerRadius(25.0)
            
            HStack {
                Spacer()
                Text("Total: \(viewModel.active.total)")
                    .font(.largeTitle)
                Spacer()
            }
            
            HStack(spacing: 20.0) {
                MWButton(text: "Edit") {
                    
                }
                MWButton(text: "Add Transactions") {
                    
                }
            }
        }
        .padding()
        .navigationBarTitle(viewModel.active.name, displayMode: .inline)
    }
}

struct BaseDetails_Previews: PreviewProvider {
    static var previews: some View {
        let active = ActiveDto(
            id: "1",
            name: "STAG Trans",
            symbol: "STAG",
            currency: "$",
            total: 10.0,
            transactions: [
                TransactionDto(id: "1", date: "10/10/2020", total: 10.0)
            ])
        
        NavigationView {
            BaseDetailsView(viewModel: BaseDetailsViewModel(active: active))
        }
    }
}
