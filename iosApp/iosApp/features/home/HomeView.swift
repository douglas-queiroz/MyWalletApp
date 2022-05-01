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
                List(viewModel.list) { type in
                    HomeListItem(item: type, onClick: {
                        viewModel.onItemSelected(item: type)
                    })
                }
                
                Text("Total: \(viewModel.total)")
                    .font(.largeTitle)
                
                HStack {
                    
                    MWButton(
                        text: "Database",
                        onClick: { viewModel.loadDatabase() }
                    ).padding()
                    
                    MWButton(
                        text: "Quotation",
                        onClick: { viewModel.collectQuotations() }
                    ).padding(.trailing)
                }
            }
            
            if viewModel.showLoading {
                MWLoading()
            }
            
        
            NavigationLink(tag: .ListView, selection: $viewModel.navigateToList, destination: listViewDestination, label: EmptyView.init)
        }.navigationTitle("Home")
    }
    
    @ViewBuilder
    private func listViewDestination() -> some View {
        if viewModel.baseListviewModel != nil {
            BaseListView(viewModel: viewModel.baseListviewModel!)
        } else {
            EmptyView()
        }
    }
}

struct LazyView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}

struct HomeListItem: View {
    
    let item: ReportItem
    let onClick: (() -> Void)
    
    var body: some View {
        Button {
            onClick()
        } label: {
            HStack {
                Text(item.name)
                Spacer()
                Text(item.total + item.percentage)
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
