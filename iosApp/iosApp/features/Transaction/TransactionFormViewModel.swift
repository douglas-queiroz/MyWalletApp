//
//  TransactionFormViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared


final class TransactionFormViewModel: ObservableObject, Identifiable {
    
    @Published var date: String = ""
    @Published var amount: String = ""
    @Published var price: String = ""
    @Published private(set) var state: TransactionFormState
    
    private let addTransactionUseCase: AddTransactionUseCase
    private let assetDto: AssetDto
    private let onCompleted: () -> Void
    
    init(
        active: AssetDto,
        state: TransactionFormState,
        onCompleted: @escaping () -> Void
    ) {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        
        self.assetDto = active
        self.state = state
        self.addTransactionUseCase = doaminModule.getAddTransactionUseCase()
        self.onCompleted = onCompleted
        self.setDefaultFormValues()
    }
    
    private func setDefaultFormValues() {
        self.date = Date().format(to: "yyyy-MM-dd")
        self.amount = "1"
    }
    
    func onSaveBtnClick() {
        
        guard let quantity = Float(self.amount) else { return }
        guard let price = Float(price) else { return }
        
        
        addTransactionUseCase.execute(
            asset: assetDto,
            date: self.date,
            quantity: quantity,
            price: price) { [weak self] _, error in
                if let error = error {
                    print(error.localizedDescription)
                } else {
                    self?.onCompleted()
                }
            }
    }
}

// MARK: - Equatable
extension TransactionFormViewModel: Equatable {
    static func == (lhs: TransactionFormViewModel, rhs: TransactionFormViewModel) -> Bool {
        lhs === rhs
    }
}


struct TransactionFormState {
    var date: String = ""
    var amount: String = ""
    var price: String = ""
}
