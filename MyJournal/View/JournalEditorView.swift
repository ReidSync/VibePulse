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
    //    WithViewStore(
    //      self.store,
    //      observe: \.view,
    //      send: { .view($0) }) { viewStore in
    //      VStack {
    //        TextEditor(text: viewStore.$contents)
    //      }
    //    }
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      VStack(spacing: 0) {
        HStack {
          Text(viewStore.journal.title)
            .font(.system(size: 25, weight: .bold))
            .foregroundColor(.palleteD)
          Spacer()
        }
        TextEditor(text: viewStore.binding(
          get: \.journal.contents,
          send: JournalEditor.Action.updateContents)
        )
      }
      .padding(.leading, 15)
      .navigationBarTitleDisplayMode(.inline)      
      .toolbar {
        ToolbarItem(placement: .principal) {
          HStack {
            Image(systemName: "sun.min.fill")
              .foregroundColor(.palleteA)
            Text(viewStore.journal.date, style: .date)
              .font(.system(size: 23, weight: .bold))
              .foregroundColor(.palleteE)
            Spacer()
            Button {
              print("edit")
            } label: {
              Image(systemName: "gear")
            }
          }
        }
      }
    }
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
