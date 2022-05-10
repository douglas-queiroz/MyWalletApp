//
//  BaseListItem.swift
//  iosApp
//
//  Created by Douglas Queiroz on 12/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct BaseListItem: Identifiable {
    let id: Int
    let name: String
    let total: String
    let asset: AssetDto
}
