import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            BaseListView(viewModel: StockViewModel())
		}
	}
}
