//
//  StringExtensions.swift
//  iosApp
//
//  Created by Douglas Queiroz on 02/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension String {
    
    func toDate(from format: String) -> Date? {
        let dateFormatterGet = DateFormatter()
        dateFormatterGet.dateFormat = format
        
        return dateFormatterGet.date(from: format)
    }
}
