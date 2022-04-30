//
//  BaseListViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 12/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class BaseListViewModel: ObservableObject {
    
    @Published var list = [BaseListItem]()
    
    func updateList(with list: [BaseListItem]) {
        self.list = list
    }
}
