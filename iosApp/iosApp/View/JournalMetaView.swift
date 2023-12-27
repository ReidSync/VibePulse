//
//  JournalMetaView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import SwiftUI
import ComposableArchitecture

struct JournalMetaView: View {
  @Environment(\.vibePulseColor) private var appThemeColor
  let store: StoreOf<JournalMeta>
  @FocusState var focus: JournalMeta.State.Field?
  
  struct ViewState: Equatable {
    @BindingViewState var focus: JournalMeta.State.Field?
    @BindingViewState var title: String
    var feeling: Feelings
    var moodFactors: Set<MoodFactors>
    var titlePlaceholder: String
    let feelings: [Feelings] = Feelings.entries
    let emojis = [
      Feelings.sad : "sad",
      Feelings.angry : "angry",
      Feelings.neutral : "neutral",
      Feelings.happy : "happy",
      Feelings.superhappy : "super_happy"
    ]
  }
  
  var body: some View {
    WithViewStore(self.store, observe: \.view, send: { $0 }) { viewStore in
      Form {
        VStack(spacing: 30) {
          feelingsField(viewStore)
          
          journalInfoField(title: "Give a title for today") {
            TextField(viewStore.titlePlaceholder, text: viewStore.$title)
              .focused(self.$focus, equals: .title)
          }
          //Spacer()
        }
        .background(appThemeColor.background.toColor())
      }
      .background(appThemeColor.background.toColor())
      .scrollContentBackground(.hidden)
      .bind(viewStore.$focus, to: self.$focus)
    }
  }
}

extension BindingViewStore<JournalMeta.State> {
  var view: JournalMetaView.ViewState {
    JournalMetaView.ViewState(
      focus: self.$focus,
      title: self.$title,
      feeling: self.feeling,
      moodFactors: self.moodFactors,
      titlePlaceholder: self.titlePlaceHolder
    )
  }
}

extension JournalMetaView {
  @ViewBuilder
  private func journalInfoField<Content: View>(
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
  private func feelingsField(
    _ viewStore: ViewStore<JournalMetaView.ViewState, JournalMeta.Action>
  )-> some View {
    journalInfoField(title: "How are you feeling?") {
      HStack(spacing: 8) {
        Spacer()
        ForEach(viewStore.feelings, id: \.self) { feeling in
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

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock)) {
      JournalMeta()
    })
}
