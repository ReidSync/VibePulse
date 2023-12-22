//
//  SolidColor+Extensions.swift
//  iosApp
//
//  Created by Reid on 2023/12/22.
//

import Foundation
import SwiftUI

public extension SolidColor {
  func toColor()-> Color {
    return Color(red: Double(r), green: Double(g), blue: Double(b), opacity: Double(a))
  }
}
