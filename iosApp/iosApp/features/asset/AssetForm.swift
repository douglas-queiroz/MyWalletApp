//
//  AssetForm.swift
//  iosApp
//
//  Created by Douglas Queiroz on 02/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AssetForm: View {

    let currencies = ["Euro", "Real", "Dolar"]
    
    @State var currency = ""
    
    @State var name: String = ""
    
    var body: some View {
        NavigationView {
            Form {
                MWTextField(
                    field: "Name",
                    value: $name
                )
                
                MWTextField(
                    field: "Symbol",
                    value: $name
                )
                
                Picker("Currency", selection: $currency) {
                    ForEach(currencies, id: \.self) { currency in
                        Text(currency)
                    }
                }
                
                MWButton(text: "Save") {
                    
                }
                
            }.navigationTitle("Asset")
        }
    }
}

struct AssetForm_Previews: PreviewProvider {
    static var previews: some View {
        AssetForm()
    }
}
