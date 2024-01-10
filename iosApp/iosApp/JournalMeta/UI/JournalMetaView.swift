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
    let type: JournalMetaViewType
    @BindingViewState var focus: JournalMeta.State.Field?
    @BindingViewState var title: String
    var feeling: Feelings
    var moodFactors: Set<MoodFactors>
    var titlePlaceholder: String    
    var weather: JournalWeather
    var location: String
    @BindingViewState var keyboardPadding: CGFloat
  }
  
  var body: some View {
    WithViewStore(self.store, observe: \.view, send: { $0 }) { viewStore in
      ZStack {
        ScrollView (.vertical, showsIndicators: false) {
          appThemeColor.background.toColor().edgesIgnoringSafeArea(.all)
          VStack(spacing: 30) {
            feelingsFieldView(viewStore)
            moodFactorFieldView(viewStore)
            weatherFieldView(viewStore)
            titleFieldView(viewStore)
          }
          .bind(viewStore.$focus, to: self.$focus)
          .padding(20)
        }
        //.scrollContentBackground(.hidden)
        //.background(appThemeColor.background.toColor())
      }
      .onAppear() {
        UINavigationBar.appearance().backgroundColor = UIColor(appThemeColor.background.toColor())
      }
//      .onChange(of: appThemeColor.background) { _, newValue in
//        UINavigationBar.appearance().backgroundColor = UIColor(appThemeColor.background.toColor())
//      }
      .task {
        await viewStore.send(.task).finish()
      }
      //.padding(.bottom, viewStore.keyboardPadding)
      .background(appThemeColor.background.toColor())
      .onTapGesture {
        self.focus = nil
      }
      
    }
  }
}

extension BindingViewStore<JournalMeta.State> {
  var view: JournalMetaView.ViewState {
    JournalMetaView.ViewState(
      type: self.type,
      focus: self.$focus,
      title: self.$title,
      feeling: self.feeling,
      moodFactors: self.moodFactors,
      titlePlaceholder: self.titlePlaceHolder,
      weather:  self.journal.weather,
      location: self.journal.location.cityName,
      keyboardPadding: self.$keyboardPadding
      
    )
  }
}

#Preview {
  JournalMetaView(
    store: Store(initialState: JournalMeta.State(journal: Journal.companion.mock, type: JournalMetaViewType.Add())) {
      JournalMeta()
    })
}
