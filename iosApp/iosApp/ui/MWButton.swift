//
//  MWButton.swift
//  iosApp
//
//  Created by Douglas Queiroz on 07/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MWButton: View {
    
    let text: String
    let onClick: () -> Void
    
    var body: some View {
        ZStack {
            Button(action: onClick) {
                Text(text)
                    .foregroundColor(Color.white)
                    .padding()
            }
        }.background(Color.blue)
            .cornerRadius(15)
    }
}

struct MWButton_Previews: PreviewProvider {
    static var previews: some View {
        MWButton(text: "Download Database", onClick: {
            
        })
    }
}
