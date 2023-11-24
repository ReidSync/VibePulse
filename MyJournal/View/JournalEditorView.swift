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
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      VStack {
        Text("editor")
      }
    }
  }
}

#Preview {
  JournalEditorView(
    store: Store(initialState: JournalEditor.State(journal: .mock)) {
      JournalEditor()
    })
}
