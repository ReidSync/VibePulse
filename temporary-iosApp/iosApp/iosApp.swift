//
//  MyJournalApp.swift
//  MyJournal
//
//  Created by Reid on 2023/11/22.
//

import SwiftUI
import ComposableArchitecture

@main
struct iosApp: App {
  var body: some Scene {
    WindowGroup {
      AppView(
        store: Store(initialState: AppFeature.State()) {
          AppFeature()._printChanges()
        }
      )
    }
  }
}
