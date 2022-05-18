//
//  AssetFormViewModel.swift
//  iosApp
//
//  Created by douglas.queiroz on 11/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class AssetFormViewModel: ObservableObject, Identifiable {
    
    @Published var currencyList = [CurrencyDto]()
    @Published var name = ""
    @Published var symbol = ""
    @Published var selectedCurrency = ""
    
    private let getCurrenciesUseCase: GetCurrenciesUseCase
    private let insertAssetUseCase: InsertAssetUseCase
    private let onComplete: () -> Void
    
    init(onComplete: @escaping (() -> Void)) {
        let databaseFactory = DatabaseDriverFactory()
        let domainModule = DomainModule(databaseDriverFactory: databaseFactory)
        getCurrenciesUseCase = domainModule.getCurrenciesUseCase()
        insertAssetUseCase = domainModule.getInsertAssetUseCase()
        self.onComplete = onComplete
        
        loadCurrencies()
    }
    
    func getAssetType() -> AssetType {
        fatalError("getAssetType method needs to be implemented")
    }
    
    func getFixedIncomeType() -> FixedIncomeType? {
        return nil
    }
    
    func getShareType() -> ShareType? {
        return nil
    }
    
    private func loadCurrencies() {
        getCurrenciesUseCase.execute { [weak self] currencies, error in
            if let currencies = currencies {
                self?.currencyList = currencies
            }
            
            if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    func onSaveClick() {
        
        let assertDto = AssetDto(
            id: "",
            name: name,
            code: symbol,
            amount: 0.0,
            currency: CurrencyDto(id: selectedCurrency, name: "", symbol: ""),
            total: 0.0,
            transactions: [TransactionDto]()
        )
        
        insertAssetUseCase.execute(
            assetDto: assertDto,
            assetType: getAssetType(),
            fixedIncomeType: getFixedIncomeType(),
            shareType: getShareType()) { [weak self] _, error in
                if let error = error {
                    print(error.localizedDescription)
                } else {
                    self?.onComplete()
                }
            }
    }
    
}


