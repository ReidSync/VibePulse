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
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      NavigationStack {
        ZStack {
          JournalHomeView(
            store: self.store.scope(
              state: \.journalHome,
              action: AppFeature.Action.journalHome)
          )
        }
      }
      .navigationTitle("My Journals")
    }
  }
}


#Preview {
  AppView(
    store: Store(initialState: AppFeature.State()) {
    AppFeature()
  })
}
