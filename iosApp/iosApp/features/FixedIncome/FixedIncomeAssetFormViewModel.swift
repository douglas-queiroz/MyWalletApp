//
//  FixedIncomeAssetFormViewModel.swift
//  iosApp
//
//  Created by douglas.queiroz on 18/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class FixedIncomeAssetFormViewModel: AssetFormViewModel {
    
    private let fixedIncomeType: FixedIncomeType
    
    init(fixedIncomeType: FixedIncomeType, onComplete: @escaping (() -> Void)) {
        self.fixedIncomeType = fixedIncomeType
        super.init(onComplete: onComplete)
    }
    
    override func getAssetType() -> AssetType {
        return AssetType.fixedincome
    }
    
    override func getFixedIncomeType() -> FixedIncomeType? {
        return fixedIncomeType
    }
    
}
