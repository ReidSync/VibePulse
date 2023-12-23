//
//  JournalHomeView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/23.
//

import SwiftUI
import ComposableArchitecture

struct JournalHomeView: View {
  @Environment(\.colorScheme) private var colorScheme
  let store: StoreOf<JournalHome>
  
  init(store: StoreOf<JournalHome>) {
    self.store = store
    
    //Use this if NavigationBarTitle is with Large Font
    UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor(SolidColor.PeriwinkleA)]
    //Use this if NavigationBarTitle is with displayMode = .inline
    UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor(SolidColor.PeriwinkleA)]
  }
  
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
                  .foregroundColor(SolidColor.CandleB);
                HStack {
                  Image(systemName: "calendar")
                    .resizable()
                    .frame(width: 11, height: 11)
                    .foregroundColor(SolidColor.SunsetA)
                  Text(journal.date.format(format: ("EE, MMM d, yyyy   h:mm:ss a")))
                    .font(.system(size: 11, weight: .light))
                    .foregroundColor(SolidColor.SunsetB)
                }
              }
            }
          }
        }
//        .listStyle(.plain)
//        .clipShape(RoundedRectangle(cornerRadius: 10))
//        .padding(.leading, 20)
//        .padding(.trailing, 20)
//        .background(SolidColor.companion.homeBackground.toColor())
        
        
        VStack {
          Spacer()
          CreateButton() {
            viewStore.send(.createButtonTapped)
          }
        }
      }
      .navigationTitle("VibePulse")
      .onChange(of: colorScheme) { _, newValue in
        viewStore.send(.darkMode(newValue == .dark))
      }
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
        .fill(SolidColor.PeriwinkleA.shadow(.drop(color: .gray, radius: 10)))
        .frame(width: 70, height: 70)
      
      
      Button(action: self.action) {
        Image(systemName: "plus")
          .resizable()
          .scaledToFit()
          .frame(width: 24, height: 24)
          .foregroundColor(SolidColor.White)
      }
    }
  }
}

#Preview {
  JournalHomeView(
    store: Store(initialState: JournalHome.State()) {
      JournalHome()
    } withDependencies: {
      $0.fileIOClient.load = { _ in
        let notebook = Notebook(journals: [
          Journal.companion.mock,
          Journal.companion.mock,
          Journal.companion.mock
        ])
        
//        try JSONEncoder().encode([
//          Journal.companion.mock
//        ])
        
        return notebook.serialize()
      }
    }
  )
}
