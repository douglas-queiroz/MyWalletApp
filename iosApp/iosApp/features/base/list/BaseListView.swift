//
//  BaseListView.swift
//  iosApp
//
//  Created by Douglas Queiroz on 12/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BaseListView: View {
    
    @ObservedObject var viewModel: BaseListViewModel
    
    var body: some View {
        ZStack {
            VStack {
                List(viewModel.list) { item in
                    NavigationLink(
                        destination: BaseDetailsView(viewModel: BaseDetailsViewModel(asset: item.asset))) {
                            BaseList(item: item)
                        }
                }
            }
        }.navigationBarTitle(Text(viewModel.title), displayMode: .inline)
            .onAppear {
                viewModel.load()
            }
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
        NavigationView {
            BaseListView(viewModel: TesteB())
        }
    }
}

class Teste: BaseListViewModel {
    
    init() {
        super.init(title: "Stock")
        
        let currency = CurrencyDto(
            id: "",
            name: "",
            symbol: ""
        )
        
        let active = AssetDto(
            id: "",
            name: "",
            code: "",
            amount: 0.0,
            currency: currency,
            total: 0.0,
            transactions: [TransactionDto]()
        )
        
        updateList(with: [
            BaseListItem(id: 1, name: "Teste", total: "R$ 1.000,00", asset: active),
            BaseListItem(id: 2, name: "Teste 2", total: "R$ 3.000,00", asset: active)
        ])
    }
}

class TesteB: BaseListViewModel {
    
    init() {
        super.init(title: "REITs")
        
        let currency = CurrencyDto(
            id: "",
            name: "",
            symbol: ""
        )
        
        let active = AssetDto(
            id: "",
            name: "",
            code: "",
            amount: 0.0,
            currency: currency,
            total: 0.0,
            transactions: [TransactionDto]()
        )
        
        updateList(with: [
            BaseListItem(id: 1, name: "Teste3", total: "R$ 1.000,00", asset: active),
            BaseListItem(id: 2, name: "Teste 4", total: "R$ 3.000,00", asset: active)
        ])
    }
}
