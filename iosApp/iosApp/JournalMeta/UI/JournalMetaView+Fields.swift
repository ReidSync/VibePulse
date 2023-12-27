//
//  JournalMetaView+Fields.swift
//  iosApp
//
//  Created by Reid on 2023/12/27.
//

import SwiftUI
import ComposableArchitecture

extension JournalMetaView {  
  @ViewBuilder
  internal func journalInfoFieldView<Content: View>(
    title: String,
    content: () -> Content
  )-> some View {
    VStack(alignment: .leading, spacing: 20) {
      Text(title)
        .font(.system(size: 20, weight: .bold))
        .foregroundColor(appThemeColor.vibeA.toColor());
      
      content()
    }
    .background(appThemeColor.background.toColor())
  }
}

extension JournalMetaView {
  @ViewBuilder
  internal func titleFieldView(
    _ viewStore: ViewStore<JournalMetaView.ViewState, JournalMeta.Action>
  )-> some View {
    journalInfoFieldView(title: "Give a title for today") {
      TextField(viewStore.titlePlaceholder, text: viewStore.$title)
        .focused(self.$focus, equals: .title)
    }
  }
}

extension JournalMetaView {
  @ViewBuilder
  internal func feelingsFieldView(
    _ viewStore: ViewStore<JournalMetaView.ViewState, JournalMeta.Action>
  )-> some View {
    journalInfoFieldView(title: "How are you feeling?") {
      HStack(spacing: 8) {
        Spacer()
        ForEach(Feelings.entries, id: \.self) { feeling in
          if let emoji = viewStore.emojis[feeling] {
            let isSelected = viewStore.feeling == feeling
            let scale = isSelected ? 1.2 : 1.0
            
            Button {
              viewStore.send(.setFeeling(feeling))
            } label: {
              Image(emoji)
                .resizable()
                .frame(width: 42, height: 42)
                .overlay(
                  Color.gray
                    .opacity(isSelected ? 0 : 0.7)
                    .clipShape(Circle())
                )
                .padding(8)
                .clipShape(Circle())
                .scaleEffect(CGFloat(scale))
            }
            .buttonStyle(PlainButtonStyle())
            .animation(.easeInOut(duration: 0.2), value: scale)
          }
        }
        Spacer()
      }
    }
  }
}

extension JournalMetaView {
  @ViewBuilder
  internal func moodFactorFieldView(
    _ viewStore: ViewStore<JournalMetaView.ViewState, JournalMeta.Action>
  )-> some View {
    journalInfoFieldView(title: "What's affecting your mood?") {
      LazyVGrid(columns: gridLayout(), spacing: 10) {
        ForEach(MoodFactors.entries, id: \.self) { moodFactor in
          //Text(moodFactor.displayName)
          moodFactorTextButton(moodFactor, viewStore.moodFactors.contains(moodFactor)) {
            viewStore.send(.setMoodFactor(moodFactor: moodFactor, selected: $0))
          }
        }
      }
    }
  }
  
  @ViewBuilder
  private func moodFactorTextButton(
    _ moodFactor: MoodFactors,
    _ isSelected: Bool,
    action: @escaping  (Bool)-> Void
  )-> some View {
    Button(action: {
      action(!isSelected)
    }) {
      Text(moodFactor.displayName)
        .font(.system(size: 16))
        .frame(minWidth: 0, maxWidth: .infinity)
        .padding()
        .background(
          Capsule()
            .fill(
              isSelected ? appThemeColor.vibeA.toColor() : appThemeColor.background.toColor()
            )
        )
        .overlay(
          Capsule()
            .stroke(appThemeColor.vibeD.toColor(), lineWidth: 2)
        )
        .foregroundColor(appThemeColor.vibeD.toColor())
    }
  }
  
  private func gridLayout() -> [GridItem] {
    let columnCount = 3
    return Array(repeating: .init(.flexible(), spacing: 10), count: columnCount)
  }
}


struct ContentView: View {
  // Sample data for buttons
  let buttonTitles = ["Button 1", "Button 2", "Button 3", "Button 4", "Button 5", "Button 6"]
  
  @State private var selectedButtons: Set<String> = []
  
  var body: some View {
    LazyVGrid(columns: gridLayout(), spacing: 10) {
      ForEach(buttonTitles, id: \.self) { title in
        Button(action: {
          toggleSelection(title)
        }) {
          Text(title)
            .frame(minWidth: 0, maxWidth: .infinity)
            .padding()
            .background(selectedButtons.contains(title) ? Color.blue : Color.gray)
            .foregroundColor(.white)
            .cornerRadius(10)
        }
      }
    }
    .padding()
  }
  
  func gridLayout() -> [GridItem] {
    let columnCount = 3 // Set the desired number of columns
    return Array(repeating: .init(.flexible(), spacing: 10), count: columnCount)
  }
  
  func toggleSelection(_ buttonTitle: String) {
    if selectedButtons.contains(buttonTitle) {
      selectedButtons.remove(buttonTitle)
    } else {
      selectedButtons.insert(buttonTitle)
    }
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView()
  }
}
