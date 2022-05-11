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
    @Published var selectedCurrency: CurrencyDto?
    
    private let getCurrenciesUseCase: GetCurrenciesUseCase
    private let insertAssetUseCase: InsertAssetUseCase
    
    init() {
        let databaseFactory = DatabaseDriverFactory()
        let domainModule = DomainModule(databaseDriverFactory: databaseFactory)
        getCurrenciesUseCase = domainModule.getCurrenciesUseCase()
        insertAssetUseCase = domainModule.getInsertAssetUseCase()
        
        
        loadCurrencies()
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
        print("Name: \(name)")
        print("Symbol: \(symbol)")
        print("Currency: \(selectedCurrency?.name ?? "null")")
    }
    
}
