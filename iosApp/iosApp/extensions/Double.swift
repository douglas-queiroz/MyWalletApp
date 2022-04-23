//
//  Double.swift
//  iosApp
//
//  Created by Douglas Queiroz on 23/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension Double {
    
    func formatTwoDigits() -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.groupingSeparator = ","
        numberFormatter.groupingSize = 3
        numberFormatter.usesGroupingSeparator = true
        numberFormatter.decimalSeparator = "."
        numberFormatter.numberStyle = .decimal
        numberFormatter.maximumFractionDigits = 2
        return numberFormatter.string(from: self as NSNumber)!
    }
}
