//
//  BaseDetailsViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct TransactionItem: Identifiable {
    let id: Int
    let date: String
    let total: String
}

struct Active {
    let name: String
    let symbol: String
    let total: String
}

class BaseDetailsViewModel: ObservableObject {
    
    @Published var active: Active
    @Published var transactionItems: [TransactionItem]
    
    
    init(active: ActiveDto) {
        self.active = Active(name: active.name, symbol: active.symbol, total: "\(active.currency) \(active.total.formatTwoDigits())")
        
        self.transactionItems = active.transactions.enumerated().map({ index, transactionDto in
            let total = active.currency + transactionDto.total.formatTwoDigits()
            return TransactionItem(id: index, date: transactionDto.date, total: total)
        })
    }
}
