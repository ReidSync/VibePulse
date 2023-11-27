//
//  JournalEditorView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import SwiftUI
import ComposableArchitecture

struct JournalEditorView: View {
  var store: StoreOf<JournalEditor>
  
  struct ViewState: Equatable {
    @BindingViewState var contents: String
  }
  
  var body: some View {
    WithViewStore(
      self.store,
      observe: \.view,
      send: { .view($0) }) { viewStore in
      VStack {
        TextEditor(text: viewStore.$contents)
      }
    }
//    WithViewStore(self.store, observe: { $0 }) { viewStore in
//        VStack {
//          TextEditor(text: viewStore.binding(
//            get: \.journal.contents,
//            send: JournalEditor.Action.update)
//          )
//        }
//      }
  }
}

extension BindingViewStore<JournalEditor.State> {
  var view: JournalEditorView.ViewState {
    JournalEditorView.ViewState(
      contents: self.$contents
    )
  }
}

#Preview {
  JournalEditorView(
    store: Store(initialState: JournalEditor.State(journal: .mock)) {
      JournalEditor()
    })
}
