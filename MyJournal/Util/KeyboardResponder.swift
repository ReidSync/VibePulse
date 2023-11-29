//
//  KeyboardClient.swift
//  MyJournal
//
//  Created by Reid on 2023/11/28.
//

import Foundation
import SwiftUI
import ComposableArchitecture

extension DependencyValues {
  var keyboardResponder: @Sendable () async -> AsyncStream<CGFloat> {
    get { self[KeyboardResponder.self] }
    set { self[KeyboardResponder.self] = newValue }
  }
}

private enum KeyboardResponder: DependencyKey {
  static let liveValue: @Sendable () async -> AsyncStream<CGFloat> = {
    await AsyncStream(
      NotificationCenter.default
        .notifications(named: UIResponder.keyboardWillShowNotification)
        .map { notification in
          await (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue.height ?? 0.0
        }
    )
  }
  static let testValue: @Sendable () async -> AsyncStream<CGFloat> = unimplemented(
    #"@Dependency(\.keyboardResponder)"#, placeholder: .finished
  )
}
