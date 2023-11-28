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
              viewStore.send(.edit)
            } label: {
              Image(systemName: "gear")
            }
          }
        }
      }
      .sheet(
        store: self.store.scope(state: \.$destination, action: { .destination($0) }),
        state: \.sheetToEdit,
        action: { .sheetToEdit($0) }
      ) { store in
        NavigationStack {
          JournalMetaView(store: store)
            .navigationTitle("Edit my journal")
            .toolbar {
              ToolbarItem(placement: .cancellationAction) {
                Button("Dismiss") {
                  viewStore.send(.dismissEditingMyJournal)
                }
              }
              ToolbarItem(placement: .confirmationAction) {
                Button("Done") {
                  viewStore.send(.doneEditingMyJournal)
                }
              }
            }
        }
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
