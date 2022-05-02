//
//  FixedIncomeList.swift
//  iosApp
//
//  Created by Douglas Queiroz on 30/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import shared

class FixedIncomeList: BaseListViewModel {
    
    private let type: FixedIncomeType
    private let fetchFixedIncomeByTypeUseCase: FetchFixedIncomeByTypeUseCase
    
    init(type: FixedIncomeType) {
        
        self.type = type
        
        let databaseFactory = DatabaseDriverFactory()
        let domainModule = DomainModule(databaseDriverFactory: databaseFactory)
        fetchFixedIncomeByTypeUseCase = domainModule.getFetchFixedIncomeByTypeUseCase()
        
        super.init(title: "Saving Account")
    }
    
    override func load() {
        fetchFixedIncomeByTypeUseCase.execute(type: self.type) { activeDtos, error in
            if let error = error {
                print(error.localizedDescription)
            }
            
            let items = activeDtos?.enumerated().map({ (index, activeDto) in
                BaseListItem(
                    id: index,
                    name: activeDto.name,
                    total: "\(activeDto.currency) \(activeDto.total.formatTwoDigits())",
                    active: activeDto
                )
            })
            
            if let items = items {
                self.updateList(with: items)
            }
        }
    }
}
