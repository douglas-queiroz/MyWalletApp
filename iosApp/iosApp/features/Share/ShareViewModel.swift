//
//  StockViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 21/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ShareViewModel: BaseListViewModel {
    
    private let type: ShareType
    private let fetchActiveUseCase: FetchShareByTypeUseCase
    
    init(type: ShareType) {
        
        self.type = type
        
        let databaseFactory = DatabaseDriverFactory()
        let domainModule = DomainModule(databaseDriverFactory: databaseFactory)
        fetchActiveUseCase = domainModule.getFetchShareByTypeUseCase()
        
        super.init()
        
        self.loadStocks()
    }
    
    
    func loadStocks() {
        fetchActiveUseCase.execute(type: self.type) { activeDtos, error in
            if let error = error {
                print(error.localizedDescription)
            }
            
            let items = activeDtos?.enumerated().map({ (index, activeDto) in
                BaseListItem(
                    id: index,
                    name: activeDto.name,
                    total: "\(activeDto.currency) \(activeDto.total.formatTwoDigits())"
                )
            })
            
            if let items = items {
                self.updateList(with: items)
            }
        }
    }
}
