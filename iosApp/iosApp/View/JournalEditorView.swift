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
            .foregroundColor(SolidColor.PeriwinkleA)
          Spacer()
        }
        TextEditor(text: viewStore.binding(
          get: \.journal.contents,
          send: JournalEditor.Action.updateContents)
        )
        //.scrollContentBackground(.hidden)
        //.background(.red)
        .clipShape(RoundedRectangle(cornerRadius: 10))

      }
      .padding(.leading, 10)
      .padding(.trailing, 10)
      .navigationBarTitleDisplayMode(.inline)
      .toolbar {
        ToolbarItem(placement: .principal) {
          HStack {
            Image(systemName: "sun.min.fill")
              .foregroundColor(SolidColor.CandleB)
            Text(viewStore.journal.date.format(format: "MMMM d, yyyy"))
              .font(.system(size: 23, weight: .bold))
              .foregroundColor(SolidColor.CandleB)
            Spacer()
            Button {
              viewStore.send(.edit)
            } label: {
              Image(systemName: "gear")
                .tint(SolidColor.SunsetA)
            }
          }
        }
      }
      //.background(SolidColor.VeryDarkGray)
      .task {
        await viewStore.send(.task).finish()
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
    store: Store(initialState: JournalEditor.State(journal: Journal.companion.mock)) {
      JournalEditor()
    })
}
