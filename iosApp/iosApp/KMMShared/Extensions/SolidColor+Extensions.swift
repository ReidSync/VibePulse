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
  
  static let PeriwinkleA: Color = SolidColor.companion.PeriwinkleA.toColor()
  static let SunsetA: Color = SolidColor.companion.SunsetA.toColor()
  static let SunsetB: Color = SolidColor.companion.SunsetB.toColor()
  static let CandleB: Color = SolidColor.companion.CandleB.toColor()
  
  
  static let PeriwinkleB: Color = SolidColor.companion.PeriwinkleB.toColor() //
  static let PeriwinkleC: Color = SolidColor.companion.PeriwinkleC.toColor() //
  static let PeriwinkleD: Color = SolidColor.companion.PeriwinkleD.toColor() //
  
  static let SeaSideA: Color = SolidColor.companion.SeaSideA.toColor() //
  static let SeaSideB: Color = SolidColor.companion.SeaSideB.toColor() //
  static let SeaSideC: Color = SolidColor.companion.SeaSideC.toColor() //
  static let SeaSideD: Color = SolidColor.companion.SeaSideD.toColor() //
  
  
  static let SunsetC: Color = SolidColor.companion.SunsetC.toColor() //
  static let SunsetD: Color = SolidColor.companion.SunsetD.toColor() //
  
  static let CandleA: Color = SolidColor.companion.CandleA.toColor() //
  
  static let CandleC: Color = SolidColor.companion.CandleC.toColor() //
  static let CandleD: Color = SolidColor.companion.CandleD.toColor() //
  
  static let Red = SolidColor.companion.Red.toColor()
  static let Green = SolidColor.companion.Green.toColor()
  static let Blue = SolidColor.companion.Blue.toColor()
  static let Cyan = SolidColor.companion.Cyan.toColor()
  static let Magenta = SolidColor.companion.Magenta.toColor()
  static let Yellow = SolidColor.companion.Yellow.toColor()
  static let Black = SolidColor.companion.Black.toColor()
  static let White = SolidColor.companion.White.toColor()
  static let VeryDarkGray = SolidColor.companion.VeryDarkGray.toColor()
  static let DarkerGray = SolidColor.companion.DarkerGray.toColor()
  static let DarkGray = SolidColor.companion.DarkGray.toColor()
  static let MidGray = SolidColor.companion.MidGray.toColor()
  static let LightGray = SolidColor.companion.LightGray.toColor()
  static let LighterGray = SolidColor.companion.LighterGray.toColor()
  static let VeryLightGray = SolidColor.companion.VeryLightGray.toColor()
  
  //static let background = SolidColor.companion.background.toColor()
  
  
  
}
