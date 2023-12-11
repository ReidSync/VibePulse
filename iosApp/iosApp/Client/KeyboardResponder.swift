//
//  KeyboardClient.swift
//  MyJournal
//
//  Created by Reid on 2023/11/28.
//

import Foundation
import SwiftUI
import ComposableArchitecture

struct KeyboardResponder: Sendable {
  var enabled: @Sendable () async -> AsyncStream<Void>
  var height: @Sendable () -> CGFloat
  var width: @Sendable () -> CGFloat
}

extension DependencyValues {
  var keyboardResponder: KeyboardResponder {
    get { self[KeyboardResponder.self] }
    set { self[KeyboardResponder.self] = newValue }
  }
}

extension KeyboardResponder: DependencyKey {
  private static var rect: CGRect?
  static let liveValue = Self(
    enabled: { 
      await AsyncStream(
        NotificationCenter.default
          .notifications(named: UIResponder.keyboardWillShowNotification)
          .map { notification in
            rect = await (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue
          }
      )
    },
    height: {
      return rect?.height ?? 0.0
    },
    width: {
      return rect?.width ?? 0.0
    }
  )
  
  static let testValue = Self(
    enabled: unimplemented("KeyboardResponder.enabled"),
    height: unimplemented("KeyboardResponder.height"),
    width: unimplemented("KeyboardResponder.width")
  )  
}
