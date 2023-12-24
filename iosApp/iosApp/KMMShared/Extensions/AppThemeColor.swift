//
//  AppThemeColor.swift
//  iosApp
//
//  Created by Reid on 2023/12/25.
//

import SwiftUI
import shared

private struct AppThemeColorsKey: EnvironmentKey {
  static let defaultValue: AppColor = RC.default_
}

extension EnvironmentValues {
  var appThemeColors: AppColor {
    get { self[AppThemeColorsKey.self] }
    set { self[AppThemeColorsKey.self] = newValue }
  }
  var vibePulseColor: VibePulseColor {
    appThemeColors.vibePulseColors
  }
}

extension View {
  func appThemeColors(_ value: AppColor) -> some View {
    environment(\.appThemeColors, value)
  }
}
