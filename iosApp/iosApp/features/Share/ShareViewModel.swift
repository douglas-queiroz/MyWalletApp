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
        
        var title = ""
        
        switch type {
        case .stock:
            title = "Stocks"
            break
        case .bitcoin:
            title = "Bitcoin"
            break
        case .gold:
            title = "Gold"
            break
        case .reit:
            title = "REITs"
            break
        default:
            title = ""
        }
        
        super.init(title: title)
    }
    
    
    override func load() {
        fetchActiveUseCase.execute(type: self.type) { activeDtos, error in
            if let error = error {
                print(error.localizedDescription)
            }
            
            let items = activeDtos?.enumerated().map({ (index, assetDto) in
                BaseListItem(
                    id: index,
                    name: assetDto.name,
                    total: "\(assetDto.currency.symbol) \(assetDto.total.formatTwoDigits())",
                    asset: assetDto
                )
            })
            
            if let items = items {
                self.updateList(with: items)
            }
        }
    }
    
    override func onAddButtonClick() {
        self.assetFormViewModel = ShareAssetFormViewModel(
            shareType: type,
            onComplete: { [weak self] in
                self?.assetFormViewModel = nil
                self?.load()
            }
        )
    }
}
