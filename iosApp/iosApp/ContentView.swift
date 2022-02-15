import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greeting()
    
    

	var body: some View {
		Text(greet)
        Button("Click here") {
            let databaseFactory = DatabaseDriverFactory()
            
            DomainModule(databaseDriverFactory: databaseFactory).getLoadDatabaseUseCase().execute { result, error in
                
                if let result = result {
                    print(result)
                }
                
                if let error = error {
                    print(error.localizedDescription)
                }
            }
            
            
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
