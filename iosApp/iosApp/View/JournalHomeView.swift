//
//  JournalHomeView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/23.
//

import SwiftUI
import ComposableArchitecture

struct JournalHomeView: View {
  let store: StoreOf<JournalHome>
  
  var body: some View {
    WithViewStore(self.store, observe: \.journals) { viewStore in
      ZStack {
        List {
          ForEach(viewStore.state) { journal in
            NavigationLink(
              state: AppFeature.Path.State.editor(JournalEditor.State(journal: journal))
            ){
              VStack(alignment: .leading) {
                Text(journal.title)
                  .font(.system(size: 18, weight: .bold))
                HStack {
                  Image(systemName: "calendar")
                    .resizable()
                    .frame(width: 11, height: 11)
                  Text(journal.date.format(format: ("EE, MMM d, yyyy   h:mm:ss a")))
                    .font(.system(size: 11, weight: .light))
                }
              }
            }
          }
        }
        
        VStack {
          Spacer()
          CreateButton() {
            viewStore.send(.createButtonTapped)
          }
        }
      }
      .navigationTitle("My Journals")
      .sheet(
        store: self.store.scope(state: \.$destination, action: { .destination($0) }),
        state: \.sheetToAdd,
        action: { .sheetToAdd($0) }
      ) { store in
        NavigationStack {
          JournalMetaView(store: store)
            .navigationTitle("New Journal")
            .toolbar {
              ToolbarItem(placement: .cancellationAction) {
                Button("Dismiss") {
                  viewStore.send(.dismissAddingNewJournal)
                }
              }
              ToolbarItem(placement: .confirmationAction) {
                Button("Add") {
                  viewStore.send(.addNewJournal)
                }
              }
            }
        }
      }
    }
    
    
  }
}


struct CreateButton: View {
  let action: () -> Void
  
  var body: some View {
    ZStack {
      Circle()
        .fill(.palleteC.shadow(.drop(color: .gray, radius: 10)))
        .frame(width: 70, height: 70)
      
      
      Button(action: self.action) {
        Image(systemName: "plus")
          .resizable()
          .scaledToFit()
          .frame(width: 24, height: 24)
          .foregroundColor(.palleteE)
      }
    }
  }
}

//#Preview {
//  JournalHomeView(
//    store: Store(initialState: JournalHome.State()) {
//      JournalHome()
//    } withDependencies: {
//      $0.fileIOClient.load = { _ in
//        let notebook = Notebook(journals: [
//          Journal.companion.mock,
//          Journal.companion.mock,
//          Journal.companion.mock
//        ])
//        try JSONEncoder().encode([
//          Journal.companion.mock
//        ])
//      }
//    }
//  )
//}
