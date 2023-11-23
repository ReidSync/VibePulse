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
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      NavigationStack {
        ZStack {
          List {
            ForEach(viewStore.journals) { journal in
              Text(journal.title)
            }
          }          
          VStack {
            Spacer()
            CreateButton() {
              print("create")
            }
          }
        }
      }
      .navigationTitle("My Journals")
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

#Preview {
  JournalHomeView(
    store: Store(initialState: JournalHome.State(journals: [.mock])) {
      JournalHome()
    })
}
