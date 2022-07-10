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
    let quantity: String
    let price: String
    let total: String
}

struct Asset {
    let name: String
    let symbol: String
    let amount: String
    let total: String
}

class BaseDetailsViewModel: ObservableObject, Identifiable {
    
    @Published var asset: Asset
    @Published var transactionItems: [TransactionItem]
    @Published private(set) var state: BaseDetailsState
    @State var show: Bool = false
    
    private var assetDto: AssetDto
    private let getActiveUseCase: GetAssetUseCase
    private let deleteTransactionUseCase: DeleteTransactionUseCase
    
    var transactionFormViewModel: Binding<TransactionFormViewModel?> {
        Binding(to: \.state.transactionFormViewModel, on: self)
    }
    
    init(asset: AssetDto, initState: BaseDetailsState = .init()) {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        
        self.deleteTransactionUseCase = doaminModule.getDeleteTransactionUseCase()
        self.getActiveUseCase = doaminModule.getGetAssetUseCase()
        self.asset = Asset(
            name: asset.name,
            symbol: asset.code,
            amount: asset.amount.formatFourDigits(),
            total: "\(asset.currency.symbol) \(asset.total.formatTwoDigits())"
        )
        self.state = initState
        self.assetDto = asset
        
        self.transactionItems = asset.transactions.enumerated().map({ index, transactionDto in
            let total = asset.currency.symbol + transactionDto.total.formatTwoDigits()
            return TransactionItem(
                id: index,
                date: transactionDto.date,
                quantity: transactionDto.quantity.formatFourDigits() + "x",
                price: asset.currency.symbol + transactionDto.price.formatTwoDigits(),
                total: total
            )
        })
    }
    
    private func updateAsset() {
        getActiveUseCase.execute(id: assetDto.id) {[weak self] assetDto, error in
            if let assetDto = assetDto {
                self?.assetDto = assetDto
                self?.asset = Asset(
                    name: assetDto.name,
                    symbol: assetDto.code,
                    amount: assetDto.amount.formatFourDigits(),
                    total: "\(assetDto.currency.symbol) \(assetDto.total.formatTwoDigits())"
                )
                self?.transactionItems = assetDto.transactions.enumerated().map({ index, transactionDto in
                    let total = assetDto.currency.symbol + transactionDto.total.formatTwoDigits()
                    return TransactionItem(
                        id: index,
                        date: transactionDto.date,
                        quantity: transactionDto.quantity.formatFourDigits() + "x",
                        price: assetDto.currency.symbol + transactionDto.price.formatTwoDigits(),
                        total: total
                    )
                })
            }
            
            if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    func addTransaction() {
        state.transactionFormViewModel = TransactionFormViewModel(
            active: assetDto,
            state: .init(),
            onCompleted: { [weak self] in
                self?.updateAsset()
                self?.state.transactionFormViewModel = nil
            }
        )
    }
    
    func deleteTransaction(indexSet: IndexSet) {
        guard let index = indexSet.first else { return }
        let transaction = assetDto.transactions[index]
        
        deleteTransactionUseCase.execute(id: transaction.id) {[weak self] _, error in
            if let error = error {
                print(error.localizedDescription)
            } else {
                self?.updateAsset()
            }
        }
        
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
