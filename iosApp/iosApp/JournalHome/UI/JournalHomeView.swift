//
//  JournalHomeView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/23.
//

import SwiftUI
import ComposableArchitecture

struct JournalHomeView: View {
  @Environment(\.vibePulseColor) private var appThemeColor
  let store: StoreOf<JournalHome>
  
  init(store: StoreOf<JournalHome>) {
    self.store = store
  }
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      ZStack {
        List {
          ForEach(viewStore.journals) { journal in
            NavigationLink(
              state: AppFeature.Path.State.editor(JournalEditor.State(journal: journal))
            ){
              VStack(alignment: .leading) {
                Text(journal.titleWithPlaceHolder)
                  .font(.system(size: 18, weight: .bold))
                  .foregroundColor(appThemeColor.vibeD.toColor());
                HStack {
                  Image(systemName: "calendar")
                    .resizable()
                    .frame(width: 11, height: 11)
                    .foregroundColor(appThemeColor.vibeB.toColor())
                  Text(journal.date.format(format: ("EE, MMM d, yyyy   h:mm:ss a")))
                    .font(.system(size: 11, weight: .light))
                    .foregroundColor(appThemeColor.vibeC.toColor())
                }
              }
            }
          }
          .onDelete { indexSet in
            guard let index = indexSet.first else {
              return
            }
            viewStore.send(.removeJournal(index: index))
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
      .onAppear {
        UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor(appThemeColor.vibeA.toColor())]
        UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor(appThemeColor.vibeA.toColor())]
      }
      .navigationTitle("VibePulse")
      .sheet(
        store: self.store.scope(state: \.$destination, action: { .destination($0) }),
        state: \.sheetToAdd,
        action: { .sheetToAdd($0) }
      ) { store in
        NavigationStack {
          JournalMetaView(store: store)
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle("New Journal")
            .toolbar {
//              ToolbarItem(placement: .principal) {
//                Text("New Journal")
//                  .font(.system(size: 20, weight: .bold))
//                  .foregroundColor(appThemeColor.vibeA.toColor());
//              }
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
  @Environment(\.vibePulseColor) private var appThemeColor
  let action: () -> Void
  
  var body: some View {
    ZStack {
      Circle()
        .fill(appThemeColor.vibeA.toColor().shadow(.drop(color: .gray, radius: 10)))
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
