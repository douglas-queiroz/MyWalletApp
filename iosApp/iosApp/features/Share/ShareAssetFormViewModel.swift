//
//  ShareAssetFormViewModel.swift
//  iosApp
//
//  Created by douglas.queiroz on 18/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ShareAssetFormViewModel: AssetFormViewModel {
    
    private let shareType: ShareType
    
    init(shareType: ShareType, onComplete: @escaping (() -> Void)) {
        self.shareType = shareType
        super.init(onComplete: onComplete)
    }
    
    override func getAssetType() -> AssetType {
        return AssetType.share
    }
    
    override func getShareType() -> ShareType? {
        return shareType
    }
}
