//
//  JournalEditorView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import SwiftUI
import ComposableArchitecture

struct JournalEditorView: View {
  @Environment(\.vibePulseColor) private var appThemeColor
  var store: StoreOf<JournalEditor>
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      VStack(spacing: 0) {
        HStack {
          Text(viewStore.journal.titleWithPlaceHolder)
            .font(.system(size: 25, weight: .bold))
            .foregroundColor(appThemeColor.vibeA.toColor())
          Spacer()
        }
        ZStack(alignment: .topLeading) {
          TextEditor(text: viewStore.binding(
            get: \.journal.contents,
            send: JournalEditor.Action.updateContents)
          )
          .font(.system(size: 20))
          .scrollContentBackground(.hidden)
          .background(appThemeColor.listBackground.toColor())
          if viewStore.journal.contents.isEmpty {
            Text(viewStore.journal.contentsWithPlaceHolder)
              .font(.system(size: 20,weight: .bold))
              .foregroundColor (Color.primary.opacity (0.25))
              .padding(.top, 10)
              .padding(.leading, 5)
          }
        }
          
        //.scrollContentBackground(.hidden)
        //.background(.red)
        .clipShape(RoundedRectangle(cornerRadius: 10))

      }
      .padding(.leading, 10)
      .padding(.trailing, 10)
      .background(appThemeColor.background.toColor())
      .navigationBarTitleDisplayMode(.inline)
      .toolbar {
        ToolbarItem(placement: .principal) {
          HStack {
            Image(systemName: "sun.min.fill")
              .foregroundColor(appThemeColor.vibeD.toColor())
            Text(viewStore.journal.date.format(format: "MMMM d, yyyy"))
              .font(.system(size: 23, weight: .bold))
              .foregroundColor(appThemeColor.vibeD.toColor())
            Spacer()
            Button {
              viewStore.send(.edit)
            } label: {
              Image(systemName: "gear")
                .tint(appThemeColor.vibeB.toColor())
            }
          }
        }
      }
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
