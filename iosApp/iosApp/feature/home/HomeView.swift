//
//  HomeView.swift
//  iosApp
//
//  Created by Douglas Queiroz on 21/03/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    
    @ObservedObject private var viewModel = HomeViewModel()
    
    
    var body: some View {
        VStack {
            Button("Download Database") {
                viewModel.loadDatabase()
            }
            
            List(viewModel.list) {
                HomeListItem(item: $0)
            }
        }
    }
}

struct HomeListItem: View {
    
    let item: ReportItem
    
    var body: some View {
        HStack {
            Text(item.name)
            Spacer()
            Text(item.total + item.percentage)
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
