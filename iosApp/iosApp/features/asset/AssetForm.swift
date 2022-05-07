//
//  AssetForm.swift
//  iosApp
//
//  Created by Douglas Queiroz on 02/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AssetForm: View {
    
    @State var name: String = ""
    
    var body: some View {
        Form {
            MWTextField(
                field: "Name",
                value: $name
            )
        }
    }
}

struct AssetForm_Previews: PreviewProvider {
    static var previews: some View {
        AssetForm()
    }
}
