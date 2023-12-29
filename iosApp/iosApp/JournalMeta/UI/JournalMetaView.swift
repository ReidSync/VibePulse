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
    @BindingViewState var keyboardPadding: CGFloat
  }
  
  var body: some View {
    WithViewStore(self.store, observe: \.view, send: { $0 }) { viewStore in
      ZStack {
        appThemeColor.background.toColor().edgesIgnoringSafeArea(.all)
        VStack(spacing: 30) {
          feelingsFieldView(viewStore)
          moodFactorFieldView(viewStore)
          titleFieldView(viewStore)
        }
        .bind(viewStore.$focus, to: self.$focus)
        .padding(20)
        //.scrollContentBackground(.hidden)
        .background(appThemeColor.background.toColor())
      }
      .onChange(of: appThemeColor.background) { _, newValue in
        UINavigationBar.appearance().backgroundColor = UIColor(appThemeColor.background.toColor())
      }
      .task {
        await viewStore.send(.task).finish()
      }
      .padding(.bottom, viewStore.keyboardPadding)
      .onTapGesture {
        self.focus = nil
      }
      
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
      titlePlaceholder: self.titlePlaceHolder,
      keyboardPadding: self.$keyboardPadding
      
    )
  }
}

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock)) {
      JournalMeta()
    })
}
