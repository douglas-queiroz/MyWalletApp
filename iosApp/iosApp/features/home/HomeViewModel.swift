//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 21/03/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI



enum Router: Hashable {
    case ListView
    case Details
}


struct ReportItem: Identifiable {
    var id: Int
    
    let name: String
    let total: String
    let percentage: String
    let reportDto: OverallReportDto
}

class HomeViewModel: ObservableObject {
    
    @Published var list = [ReportItem]()
    @Published var showLoading = false
    @Published var total = "€ 0.00"
    @Published var navigateToList: Router? = nil
    
    
    @Published var baseListviewModel: BaseListViewModel?
    
    private let loadDatabaseUseCase: LoadDatabaseUseCase
    private let calculateOverallReportUseCase: CalculateOverallReportUseCase
    private let collectQuotationUseCase: CollectQuotationsUseCase
    
    init() {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        loadDatabaseUseCase = doaminModule.getLoadDatabaseUseCase()
        calculateOverallReportUseCase = doaminModule.getCalculateOverallReportUseCase()
        collectQuotationUseCase = doaminModule.getCollectionQuotationsUseCase()
    }
    
    func updateReport() {
        showLoading = true
        
        calculateOverallReportUseCase.execute { [weak self] result, error in
            
            self?.list = [ReportItem]()
            var total = 0.0
            
            if let result = result {
                
                let sortedResult = result.sorted { dto1, dto2 in
                    dto1.total > dto2.total
                }
                
                sortedResult.enumerated().forEach { i, reportDao in
                    total += reportDao.total
                    
                    let itemTotal = reportDao.total.formatTwoDigits()
                    let itemPerc = reportDao.percentage.formatTwoDigits()
                    let item = ReportItem(id: i, name: reportDao.name, total: "€ \(itemTotal)", percentage: "(\(itemPerc)%)", reportDto: reportDao)
                    self?.list.append(item)
                }
            }
            
            let totalStr = total.formatTwoDigits()
            self?.total = "€ \(totalStr)"
            
            
            if let error = error {
                print(error.localizedDescription)
            }
            
            self?.showLoading = false
        }
    }
    
    func loadDatabase() {
        showLoading = true
        
//        loadDatabaseUseCase.execute { result, error in
//            if result != nil {
//                weak var selfWeak = self
//                selfWeak?.updateReport()
//            }
//            
//            if let error = error {
//                print(error.localizedDescription)
//            }
//            
//            self.showLoading = false
//        }
    }
    
    func collectQuotations() {
        self.showLoading = true
        
        collectQuotationUseCase.execute { result, error in
            
            print("Quotation Finished")
            
            if let error = error {
                print(error.localizedDescription)
            } else {
                self.updateReport()
            }
            
            self.showLoading = false
        }
    }
    
    func onItemSelected(item: ReportItem) {
        if let shareType = item.reportDto.shareType {
            navigateToList = .ListView
            baseListviewModel = ShareViewModel(type: shareType)
        }
        
        if let fixedIncome = item.reportDto.fixedIncomeType {
            navigateToList = .ListView
            baseListviewModel = FixedIncomeList(type: fixedIncome)
        }
    }
}
