//
//  MWDetailText.swift
//  iosApp
//
//  Created by Douglas Queiroz on 01/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MWDetailText: View {
    
    let field: String
    let value: String
    
    var body: some View {
        HStack {
            Text("\(field):")
                .bold()
            Text(value)
            Spacer()
        }
    }
}

struct MWDetailText_Previews: PreviewProvider {
    
    static var previews: some View {
        MWDetailText(field: "Name", value: "STAG Industrial")
    }
}
