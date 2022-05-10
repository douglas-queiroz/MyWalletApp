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

struct Asset {
    let name: String
    let symbol: String
    let total: String
}

class BaseDetailsViewModel: ObservableObject, Identifiable {
    
    @Published var asset: Asset
    @Published var transactionItems: [TransactionItem]
    @Published private(set) var state: BaseDetailsState
    @State var show: Bool = false
    
    private var assetDto: AssetDto
    private let getActiveUseCase: GetAssetUseCase
    
    var transactionFormViewModel: Binding<TransactionFormViewModel?> {
        Binding(to: \.state.transactionFormViewModel, on: self)
    }
    
    init(asset: AssetDto, initState: BaseDetailsState = .init()) {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        
        self.getActiveUseCase = doaminModule.getGetAssetUseCase()
        self.asset = Asset(
            name: asset.name,
            symbol: asset.code,
            total: "\(asset.currency.symbol) \(asset.total.formatTwoDigits())"
        )
        self.state = initState
        self.assetDto = asset
        
        self.transactionItems = asset.transactions.enumerated().map({ index, transactionDto in
            let total = asset.currency.symbol + transactionDto.total.formatTwoDigits()
            return TransactionItem(id: index, date: transactionDto.date, total: total)
        })
    }
    
    private func updateActive() {
        getActiveUseCase.execute(id: assetDto.id) {[weak self] assetDto, error in
            if let assetDto = assetDto {
                self?.assetDto = assetDto
                self?.asset = Asset(
                    name: assetDto.name,
                    symbol: assetDto.code,
                    total: "\(assetDto.currency) \(assetDto.total.formatTwoDigits())"
                )
                self?.transactionItems = assetDto.transactions.enumerated().map({ index, transactionDto in
                    let total = assetDto.currency.symbol + transactionDto.total.formatTwoDigits()
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
            active: assetDto,
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
