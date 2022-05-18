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
    @Published var assetFormViewModel: AssetFormViewModel?
    
    init(title: String) {
        self.title = title
    }
    
    func load() {
        fatalError("Load method needs to be implemented")
    }
    
    func updateList(with list: [BaseListItem]) {
        self.list = list
    }
    
    func onAddButtonClick() {
        
    }
}
