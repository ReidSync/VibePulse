//
//  JournalMetaView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import SwiftUI
import ComposableArchitecture

struct JournalMetaView: View {
  let store: StoreOf<JournalMeta>
  @FocusState var focus: JournalMeta.State.Field?
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      Form {
        Section {
//          TextField("Title", text: viewStore.$journal.title)
//            .focused(self.$focus, equals: .title)
        } header: {
          Text("Journal Info")
        }
      }
      .bind(viewStore.$focus, to: self.$focus)
    }
  }
}

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock)) {
      JournalMeta()
    })
}
