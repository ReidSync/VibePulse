//
//  JournalMetaView.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import SwiftUI
import ComposableArchitecture

struct JournalMetaView: View {
  @Environment(\.vibePulseColor) internal var appThemeColor
  let store: StoreOf<JournalMeta>
  @FocusState var focus: JournalMeta.State.Field?
  
  struct ViewState: Equatable {
    @BindingViewState var focus: JournalMeta.State.Field?
    @BindingViewState var title: String
    var feeling: Feelings
    var moodFactors: Set<MoodFactors>
    var titlePlaceholder: String
    //let feelings: [Feelings] = Feelings.entries
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
      VStack(spacing: 30) {
        feelingsFieldView(viewStore)
        moodFactorFieldView(viewStore)
        titleFieldView(viewStore)
      }
      .bind(viewStore.$focus, to: self.$focus)
      .padding(20)
      .background(appThemeColor.background.toColor())
      
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

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock)) {
      JournalMeta()
    })
}
