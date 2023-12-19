//
//  UUID+Extensions.swift
//  iosApp
//
//  Created by Reid on 2023/12/19.
//

import Foundation
import shared

extension shared.UUID {
  static func randomUUID()-> UUID {
    return UUID.companion.randomUUID()
  }
}
