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
  var willShow: @Sendable () async -> AsyncStream<Void>
  var willHide: @Sendable () async -> AsyncStream<Void>
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
//  private static let keyboardNotifications:[NSNotification.Name] = [
//    UIResponder.keyboardWillShowNotification,
//    UIResponder.keyboardDidShowNotification,
//    UIResponder.keyboardWillHideNotification,
//    UIResponder.keyboardDidHideNotification]
  
  static let liveValue = Self(
    willShow: {
      await AsyncStream(
        NotificationCenter.default
          .notifications(named: UIResponder.keyboardWillShowNotification)
          .map { notification in
            rect = await (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue
          }
      )
    },
    willHide: {
      await AsyncStream(
        NotificationCenter.default
          .notifications(named: UIResponder.keyboardWillHideNotification)
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
    willShow: unimplemented("KeyboardResponder.willShow"),
    willHide: unimplemented("KeyboardResponder.willHide"),
    height: unimplemented("KeyboardResponder.height"),
    width: unimplemented("KeyboardResponder.width")
  )  
}
