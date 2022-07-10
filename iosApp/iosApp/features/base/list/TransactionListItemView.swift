//
//  TransactionListItemView.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TransactionListItemView: View {
    
    let item: TransactionItem
    
    var body: some View {
        VStack {
            HStack {
                Text(item.date)
                Spacer()
                Text(item.quantity)
                Text(item.price)
            }.padding(.top, 20)
            
            HStack {
                Spacer()
                Text("Total:")
                Text(item.total)
            }.padding(.bottom, 20)
        }
    }
}

struct TransactionListItemView_Previews: PreviewProvider {
    static var previews: some View {
        TransactionListItemView(item: TransactionItem(
            id: 1,
            date: "10/10/2020",
            quantity: "10x",
            price: "R$ 10.00",
            total: "R$ 10.00"
        ))
    }
}
