//
//  AppView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/22.
//

import SwiftUI
import ComposableArchitecture

struct AppView: View {
  let store: StoreOf<AppFeature>
  
  var body: some View {
    NavigationStackStore(self.store.scope(state: \.path, action: { .path($0) })) {
      JournalHomeView(
        store: self.store.scope(
          state: \.journalHome,
          action: AppFeature.Action.journalHome)
      )
    } destination: { path in
      switch path {
      case .editor:
        CaseLet(
          \AppFeature.Path.State.editor,
           action: AppFeature.Path.Action.editor,
           then: JournalEditorView.init(store:)
        )
      }
    }
  }
}


#Preview {
  AppView(
    store: Store(initialState: AppFeature.State()) {
      AppFeature()
    })
}
