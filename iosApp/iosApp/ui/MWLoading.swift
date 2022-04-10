//
//  MWLoading.swift
//  iosApp
//
//  Created by Douglas Queiroz on 09/04/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MWLoading: View {
    var body: some View {
        ZStack {
            ProgressView()
                .frame(width: 70, height: 70)
                .scaleEffect(2)
                
        }.background(Color.white)
            .cornerRadius(16)
    }
}

struct MWLoading_Previews: PreviewProvider {
    static var previews: some View {
        MWLoading()
    }
}
