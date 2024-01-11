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
  let store: StoreOf<JournalEditor>
  @FocusState var focus: JournalEditor.State.Field?
  
  init(store: StoreOf<JournalEditor>) {
    self.store = store
  }
  
  var body: some View {
    WithViewStore(self.store, observe: { $0 }) { viewStore in
      VStack(spacing: 0) {
        HStack(spacing: 0) {
          if let emoji = FeelingEmojis[viewStore.journal.feeling] {
            Image(emoji)
              .resizable()
              .scaledToFill()
              .frame(width: 25, height: 25)
              .padding(.trailing, 20)
          }
          
          Text(viewStore.journal.titleWithPlaceHolder)
            .font(.system(size: 25, weight: .bold))
            .foregroundColor(appThemeColor.vibeA.toColor())
          Spacer()
        }
        
        if !viewStore.journal.moodFactors.isEmpty {
          ScrollView(
            .horizontal,
            showsIndicators: false) {
              LazyHStack {
                ForEach(Array(viewStore.journal.moodFactors), id: \.self) { moodFactor in
                  Text(moodFactor.displayName)
                    .font(.system(size: 16))
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .padding(3)
                    .overlay(
                      RoundedRectangle(cornerRadius: 4)
                        .stroke(appThemeColor.vibeD.toColor(), lineWidth: 1)
                    )
                    .foregroundColor(appThemeColor.vibeD.toColor())
                }
              }
              .frame(height: 30)
              .padding(.leading, 1)
            }
            .padding(10)
        }
        
        ZStack(alignment: .topLeading) {
          TextEditor(text: viewStore.binding(
            get: \.journal.contents,
            send: JournalEditor.Action.updateContents)
          )
          .font(.system(size: 20))
          .focused(self.$focus, equals: .editor)
          .foregroundColor(appThemeColor.vibeC.toColor())
          .scrollContentBackground(.hidden)
          .background(appThemeColor.listBackground.toColor())
          
          if viewStore.journal.contents.isEmpty {
            Text(viewStore.journal.contentsWithPlaceHolder)
              .font(.system(size: 20,weight: .bold))
              .foregroundColor (appThemeColor.vibeC.toColor().opacity (0.25))
              .padding(.top, 10)
              .padding(.leading, 5)
          }
        }          
        .clipShape(RoundedRectangle(cornerRadius: 10))
      }
      .bind(viewStore.$focus, to: self.$focus)
      .padding(15)
      .background(appThemeColor.background.toColor())
      .navigationBarTitleDisplayMode(.inline)
      .toolbar {
        ToolbarItem(placement: .principal) {
          HStack(spacing: 0) {
            if let weatherIcon = WeatherIcons[viewStore.journal.weather.weatherInfo] {
              Image(weatherIcon)
                .resizable()
                .scaledToFill()
                .frame(width: 36, height: 36)
                .padding(.trailing, 20)
            }
            
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
      .onTapGesture {
        self.focus = nil
      }
      .sheet(
        store: self.store.scope(state: \.$destination, action: { .destination($0) }),
        state: \.sheetToEdit,
        action: { .sheetToEdit($0) }
      ) { store in
        NavigationStack {
          JournalMetaView(store: store)
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle("Edit my journal")
            .toolbar {
//              ToolbarItem(placement: .principal) {
//                Text("Edit my journal")
//                  .font(.system(size: 20, weight: .bold))
//                  .foregroundColor(appThemeColor.vibeA.toColor());
//              }
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
