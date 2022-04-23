//
//  BaseListView.swift
//  iosApp
//
//  Created by Douglas Queiroz on 12/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BaseListView: View {
    
    @ObservedObject var viewModel: BaseListViewModel
    
    var body: some View {
        ZStack {
            VStack {
                List(viewModel.list) { BaseList(item: $0) }
            }
        }.navigationBarTitle(Text("Stocks"), displayMode: .inline)
    }
}
 
struct BaseList: View {
    
    let item: BaseListItem
    
    var body: some View {
        HStack {
            Text(item.name)
            Spacer()
            Text(item.total)
        }
    }
}

struct BaseListView_Previews: PreviewProvider {
    static var previews: some View {
        BaseListView(viewModel: TesteB())
    }
}

class Teste: BaseListViewModel {
    
    override init() {
        super.init()
        updateList(with: [
            BaseListItem(id: 1, name: "Teste", total: "R$ 1.000,00"),
            BaseListItem(id: 2, name: "Teste 2", total: "R$ 3.000,00")
        ])
    }
}

class TesteB: BaseListViewModel {
    
    override init() {
        super.init()
        updateList(with: [
            BaseListItem(id: 1, name: "Teste3", total: "R$ 1.000,00"),
            BaseListItem(id: 2, name: "Teste 4", total: "R$ 3.000,00")
        ])
    }
}
