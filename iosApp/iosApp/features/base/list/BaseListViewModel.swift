//
//  BaseListViewModel.swift
//  iosApp
//
//  Created by Douglas Queiroz on 12/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class BaseListViewModel: ObservableObject {
    
    let title: String
    @Published var list = [BaseListItem]()
    
    init(title: String) {
        self.title = title
    }
    
    func load() {
        assertionFailure()
    }
    
    func updateList(with list: [BaseListItem]) {
        self.list = list
    }
}
