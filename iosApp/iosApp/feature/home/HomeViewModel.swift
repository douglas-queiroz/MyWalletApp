//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 21/03/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct ReportItem: Identifiable {
    var id: Int
    
    let name: String
    let total: String
    let percentage: String
}

class HomeViewModel: ObservableObject {
    
    @Published var list = [ReportItem(id: 0,name: "TOTAL", total: "€ 10", percentage: "")]
    @Published var showLoading = false
    
    private let loadDatabaseUseCase: LoadDatabaseUseCase
    private let calculateOverallReportUseCase: CalculateOverallReportUseCase
    private let collectQuotationUseCase: CollectQuotationsUseCase
    
    init() {
        let databaseFactory = DatabaseDriverFactory()
        let doaminModule = DomainModule(databaseDriverFactory: databaseFactory)
        loadDatabaseUseCase = doaminModule.getLoadDatabaseUseCase()
        calculateOverallReportUseCase = doaminModule.getCalculateOverallReportUseCase()
        collectQuotationUseCase = doaminModule.getCollectionQuotationsUseCase()
        getReport()
    }
    
    private func getReport() {
        showLoading = true
        
        calculateOverallReportUseCase.execute { result, error in
            
            weak var selfWeak = self
            selfWeak?.list = [ReportItem]()
            var total = 0.0
            
            if let result = result {
                
                let sortedResult = result.sorted { dto1, dto2 in
                    dto1.total > dto2.total
                }
                
                sortedResult.enumerated().forEach { i, reportDao in
                    total += reportDao.total
                    
                    let itemTotal = reportDao.total.formatTwoDigits()
                    let itemPerc = reportDao.percentage.formatTwoDigits()
                    let item = ReportItem(id: i, name: reportDao.name, total: "€ \(itemTotal)", percentage: "(\(itemPerc)%)")
                    selfWeak?.list.append(item)
                }
            }
            
            let totalStr = total.formatTwoDigits()
            let totalItem = ReportItem(id: result?.count ?? 0,name: "TOTAL", total: "€ \(totalStr)", percentage: "")
            selfWeak?.list.append(totalItem)
            
            if let error = error {
                print(error.localizedDescription)
            }
            
            self.showLoading = false
        }
    }
    
    func loadDatabase() {
        showLoading = true
        
        loadDatabaseUseCase.execute { result, error in
            if result != nil {
                weak var selfWeak = self
                selfWeak?.getReport()
            }
            
            if let error = error {
                print(error.localizedDescription)
            }
            
            self.showLoading = false
        }
    }
    
    func collectQuotations() {
        self.showLoading = true
        
        collectQuotationUseCase.execute { result, error in
            
            print("Quotation Finished")
            
            if let error = error {
                print(error.localizedDescription)
            } else {
                self.getReport()
            }
            
            self.showLoading = false
        }
    }
}
