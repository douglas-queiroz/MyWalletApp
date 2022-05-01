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
        HStack {
            Text(item.date)
            Spacer()
            Text(item.total)
        }
    }
}

struct TransactionListItemView_Previews: PreviewProvider {
    static var previews: some View {
        TransactionListItemView(item: TransactionItem(id: 1, date: "10/10/2020", total: "$10.00"))
    }
}
