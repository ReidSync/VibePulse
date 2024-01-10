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
    VStack(alignment: .leading, spacing: 10) {
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
        .padding(5)
        .overlay(
          RoundedRectangle(cornerRadius: 2)
            .stroke(appThemeColor.vibeD.toColor(), lineWidth: 1)
        )
        .foregroundColor(appThemeColor.vibeD.toColor())
        //.ignoresSafeArea(.keyboard)
        
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
          if let emoji = FeelingEmojis[feeling] {
            let isSelected = viewStore.feeling == feeling
            let scale = isSelected ? 1.25 : 1.0
            
            Button {
              viewStore.send(.setFeeling(feeling))
            } label: {
              Image(emoji)
                .resizable()
                .scaledToFill()
                .frame(width: 44, height: 44)
                .scaleEffect(CGFloat(scale))
                .clipped()
                .clipShape(Circle())
                .saturation(isSelected ? 1 : 0)
//                .overlay(
//                  Color.gray
//                    .opacity(isSelected ? 0 : 0.7)
//                    .clipShape(Circle())
//                )
                .scaleEffect(CGFloat(scale))
                .padding(8)
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
          RoundedRectangle(cornerRadius: 20)
            .fill(
              isSelected ? appThemeColor.vibeA.toColor() : appThemeColor.background.toColor()
            )
        )
        .overlay(
          RoundedRectangle(cornerRadius: 20)
            .stroke(appThemeColor.vibeD.toColor(), lineWidth: 2)
        )
        .foregroundColor(appThemeColor.vibeC.toColor())
    }
  }
  
  private func gridLayout() -> [GridItem] {
    let columnCount = 3
    return Array(repeating: .init(.flexible(), spacing: 10), count: columnCount)
  }
}


extension JournalMetaView {
  @ViewBuilder
  internal func weatherFieldView(
    _ viewStore: ViewStore<JournalMetaView.ViewState, JournalMeta.Action>
  )-> some View {
    journalInfoFieldView(title: "Weather") {
      HStack {
        if let emoji = WeatherIcons[viewStore.weather.weatherInfo] {
          Image(emoji)
            .resizable()
            .scaledToFill()
            .frame(width: 72, height: 72)
          //.padding(8)
        }
        
        Text(viewStore.location)
          .font(.system(size: 18))
        //.padding(5)
          .foregroundColor(appThemeColor.vibeD.toColor())
        
        Spacer()
        
        if viewStore.type == .add {
          Button(action: {
            viewStore.send(.getWeatherToday)
          }) {
            switch viewStore.gettingWeatherState {
            case is GettingWeatherState.Done:
              WeatherInteractionView("Refresh")
              
            case is GettingWeatherState.Loading:
              ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: appThemeColor.vibeC.toColor()))
                .frame(width: 60, height: 10)
                .padding()
                .background(
                  RoundedRectangle(cornerRadius: 20)
                    .fill(appThemeColor.vibeA.toColor())
                )
            
            //case let weatherError as GettingWeatherState.Error:
            case is GettingWeatherState.Error:
              //weatherError.error // Alert?
              WeatherInteractionView("Error")
         
            //case let weather as GettingWeatherState.Success:
            case is GettingWeatherState.Success:
              //weather.data
              WeatherInteractionView("Success")
            default:
              EmptyView()
            }
          }
        }
      }
    }
  }
  
  internal func WeatherInteractionView(
    _ text: String
  )-> some View {
    Text(text)
      .font(.system(size: 13))
      .frame(minWidth: 0, maxWidth: 60)
      .padding()
      .background(
        RoundedRectangle(cornerRadius: 20)
          .fill(appThemeColor.vibeA.toColor())
      )
      .foregroundColor(appThemeColor.vibeC.toColor())
  }
}
