import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greeting()
    
    

	var body: some View {
		Text(greet)
        Button("Download Database") {
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
        
        Button("Load Info") {
            let databaseFactory = DatabaseDriverFactory()
            let calculateReportUseCase = DomainModule(databaseDriverFactory: databaseFactory).getCalculateOverallReportUseCase()
            calculateReportUseCase.execute { result, error in
                if let result = result {
                    print(result)
                    
                    var a = 0.0
                    result.forEach { reportDao in
                        a += reportDao.total
                    }
                    
                    print(a)
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
