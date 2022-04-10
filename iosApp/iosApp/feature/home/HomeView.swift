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
        
        
        ZStack {
            VStack {
                List(viewModel.list) { HomeListItem(item: $0) }
                
                HStack {
                    MWButton(
                        text: "Database",
                        onClick: { viewModel.loadDatabase() }
                    )
                    MWButton(
                        text: "Quotation",
                        onClick: { viewModel.collectQuotations() }
                    )
                }
            }
            
            if viewModel.showLoading {
                MWLoading()
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
