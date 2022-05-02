//
//  BaseDetailsViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

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

class BaseDetailsViewModel: ObservableObject, Identifiable {
    
    @Published var active: Active
    @Published var transactionItems: [TransactionItem]
    @Published private(set) var state: BaseDetailsState
    @State var show: Bool = false
    
    private var activeDto: ActiveDto
    private let getActiveUseCase: GetActiveUseCase
    
    var transactionFormViewModel: Binding<TransactionFormViewModel?> {
        Binding(to: \.state.transactionFormViewModel, on: self)
    }
    
    init(active: ActiveDto, initState: BaseDetailsState = .init()) {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        
        self.getActiveUseCase = doaminModule.getGetActiveUseCase()
        self.active = Active(name: active.name, symbol: active.symbol, total: "\(active.currency) \(active.total.formatTwoDigits())")
        self.state = initState
        self.activeDto = active
        
        self.transactionItems = active.transactions.enumerated().map({ index, transactionDto in
            let total = active.currency + transactionDto.total.formatTwoDigits()
            return TransactionItem(id: index, date: transactionDto.date, total: total)
        })
    }
    
    private func updateActive() {
        getActiveUseCase.execute(id: activeDto.id) {[weak self] activeDto, error in
            if let activeDto = activeDto {
                self?.activeDto = activeDto
                self?.active = Active(name: activeDto.name, symbol: activeDto.symbol, total: "\(activeDto.currency) \(activeDto.total.formatTwoDigits())")
                self?.transactionItems = activeDto.transactions.enumerated().map({ index, transactionDto in
                    let total = activeDto.currency + transactionDto.total.formatTwoDigits()
                    return TransactionItem(id: index, date: transactionDto.date, total: total)
                })
            }
            
            if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    func addTransaction() {
        state.transactionFormViewModel = TransactionFormViewModel(
            active: activeDto,
            state: .init(),
            onCompleted: { [weak self] in
                self?.updateActive()
                self?.state.transactionFormViewModel = nil
            }
        )
    }
}

// MARK: - Equatable
extension BaseDetailsViewModel: Equatable {
    static func == (lhs: BaseDetailsViewModel, rhs: BaseDetailsViewModel) -> Bool {
        lhs === rhs
    }
}

struct BaseDetailsState {
    var transactionFormViewModel: TransactionFormViewModel? = nil
    var showTransactionForm: Bool = false
    var name: String = ""
}
