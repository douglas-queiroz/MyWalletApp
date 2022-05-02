//
//  MWField.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MWTextField: View {
    
    let field: String
    let keyboardType: UIKeyboardType
    @Binding var value: String
    
    init(field: String, keyboardType: UIKeyboardType = .default, value: Binding<String>) {
        self.field = field
        self.keyboardType = keyboardType
        _value = value
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField(text: $value) {
                Text(field)
            }.keyboardType(keyboardType)
        }
    }
}

struct MWTextField_Previews: PreviewProvider {
    
    @State static var name = ""
    
    static var previews: some View {
        MWTextField(field: "Name", value: $name)
    }
}
