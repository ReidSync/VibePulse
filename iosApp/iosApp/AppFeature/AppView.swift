//
//  AppView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/22.
//

import SwiftUI
import ComposableArchitecture

struct AppView: View {
  @Environment(\.colorScheme) private var colorScheme
  @Environment(\.vibePulseColor) private var appThemeColor
  let store: StoreOf<AppFeature>
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
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
      .onAppear {
        viewStore.send(.darkMode(colorScheme == .dark))
      }
      .onChange(of: colorScheme) { _, newValue in
        viewStore.send(.darkMode(newValue == .dark))
      }
      .appThemeColors(viewStore.themeColor)
      .accentColor(appThemeColor.vibeB.toColor())
      
    }
  }
}


#Preview {
  AppView(
    store: Store(initialState: AppFeature.State()) {
      AppFeature()
    })
}
