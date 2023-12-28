//
//  JournalHomeView+List.swift
//  iosApp
//
//  Created by Reid on 2023/12/28.
//

import SwiftUI
import ComposableArchitecture

extension JournalHomeView {
  @ViewBuilder
  internal func journalListView(
    _ viewStore: ViewStore<JournalHome.State, JournalHome.Action>
  )-> some View {
    List {
      ForEach(viewStore.journals) { journal in
        NavigationLink(
          state: AppFeature.Path.State.editor(JournalEditor.State(journal: journal))
        ) {
          journalListItemView(viewStore, journal: journal)
        }
      }
      .onDelete { indexSet in
        guard let index = indexSet.first else {
          return
        }
        viewStore.send(.removeJournal(index: index))
      }
    }
    .scrollContentBackground(.hidden)
  }
}

extension JournalHomeView {
  @ViewBuilder
  internal func journalListItemView(
    _ viewStore: ViewStore<JournalHome.State, JournalHome.Action>,
    journal: Journal
  )-> some View {
    HStack(spacing: 0) {
      Text("")
      if let emoji = FeelingEmojis[journal.feeling] {
        Image(emoji)
          .resizable()
          .frame(width: 50, height: 50)
          .clipped()
          .clipShape(Circle())
          .padding(.trailing, 10)
      }
      
      VStack(alignment: .leading, spacing: 0) {
        Text(journal.date.format(format: ("EE, MMM d, yyyy   h:mm a")))
          .font(.system(size: 10, weight: .light))
          .foregroundColor(appThemeColor.vibeC.toColor())
        Text(journal.feeling.displayName)
          .font(.system(size: 20, weight: .bold))
          .foregroundColor(appThemeColor.vibeB.toColor())
        
        if !journal.title.isEmpty {
          Text(journal.titleWithPlaceHolder)
            .font(.system(size: 14, weight: .medium))
            .foregroundColor(appThemeColor.vibeD.toColor())
        }
        if !journal.moodFactors.isEmpty {
          ScrollView(
            .horizontal,
            showsIndicators: false) {
              LazyHStack {
                ForEach(Array(journal.moodFactors), id: \.self) { moodFactor in
                  Text(moodFactor.displayName)
                    .font(.system(size: 12))
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .padding(3)
                    .overlay(
                      RoundedRectangle(cornerRadius: 20)
                        .stroke(appThemeColor.vibeD.toColor(), lineWidth: 1)
                    )
                    .foregroundColor(appThemeColor.vibeD.toColor())
                }
              }
              .padding(.leading, 1)
              .padding(.trailing, 1)
            }
        }
      }
      .padding(.top, 2)
      .padding(.bottom, 2)
    }
  }
}
