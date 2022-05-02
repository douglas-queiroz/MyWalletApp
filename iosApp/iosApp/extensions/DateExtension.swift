//
//  DateExtension.swift
//  iosApp
//
//  Created by Douglas Queiroz on 02/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension Date {
    
    func format(to format: String) -> String {
        let dateFormatterGet = DateFormatter()
        dateFormatterGet.dateFormat = format
        
        return dateFormatterGet.string(from: self)
    }
}
