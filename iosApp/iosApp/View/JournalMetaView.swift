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
  
  struct ViewState: Equatable {
    @BindingViewState var focus: JournalMeta.State.Field?
    @BindingViewState var title: String
    var titlePlaceholder: String
  }
  
  var body: some View {
    WithViewStore(self.store, observe: \.view, send: { $0 }) { viewStore in
      Form {
        Section {
          TextField(viewStore.titlePlaceholder, text: viewStore.$title)
            .focused(self.$focus, equals: .title)
        } header: {
          Text("Journal Info")
        }
      }
      .bind(viewStore.$focus, to: self.$focus)
    }
  }
}

extension BindingViewStore<JournalMeta.State> {
  var view: JournalMetaView.ViewState {
    JournalMetaView.ViewState(
      focus: self.$focus,
      title: self.$title,
      titlePlaceholder: self.titlePlaceHolder
    )
  }
}

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock)) {
      JournalMeta()
    })
}
