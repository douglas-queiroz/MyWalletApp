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
    
    private let loadDatabaseUseCase: LoadDatabaseUseCase
    private let calculateOverallReportUseCase: CalculateOverallReportUseCase
    
    init() {
        let databaseFactory = DatabaseDriverFactory()
        loadDatabaseUseCase = DomainModule(databaseDriverFactory: databaseFactory).getLoadDatabaseUseCase()
        calculateOverallReportUseCase = DomainModule(databaseDriverFactory: databaseFactory).getCalculateOverallReportUseCase()
        getReport()
    }
    
    private func getReport() {
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
                    
                    let itemTotal = self.df2so(reportDao.total)
                    let itemPerc = self.df2so(reportDao.percentage)
                    let item = ReportItem(id: i, name: reportDao.name, total: "€ \(itemTotal)", percentage: "(\(itemPerc)%)")
                    selfWeak?.list.append(item)
                }
            }
            
            let totalStr = self.df2so(total)
            let totalItem = ReportItem(id: result?.count ?? 0,name: "TOTAL", total: "€ \(totalStr)", percentage: "")
            selfWeak?.list.append(totalItem)
            
            if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    func loadDatabase() {
        loadDatabaseUseCase.execute { result, error in
            if result != nil {
                weak var selfWeak = self
                selfWeak?.getReport()
            }
            
            if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    private func df2so(_ price: Double) -> String{
        let numberFormatter = NumberFormatter()
        numberFormatter.groupingSeparator = ","
        numberFormatter.groupingSize = 3
        numberFormatter.usesGroupingSeparator = true
        numberFormatter.decimalSeparator = "."
        numberFormatter.numberStyle = .decimal
        numberFormatter.maximumFractionDigits = 2
        return numberFormatter.string(from: price as NSNumber)!
    }
}
