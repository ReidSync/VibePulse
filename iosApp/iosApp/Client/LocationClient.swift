//
//  LocationClient.swift
//  iosApp
//
//  Created by Reid on 2024/01/04.
//

import Foundation
import Dependencies
import CoreLocation
import ComposableArchitecture

struct LocationClient {
  enum Action {
    case didGet(CLLocation)
    case didFailed(LocationError)
  }
  
  enum LocationError: Error {
    case permissionDenied
    case locationNotAvailable
  }
  
  var getCurrentLocation: @Sendable () async throws -> JournalLocation
}

extension LocationClient: DependencyKey {
  static var liveValue: Self {
    //let locationManager = LocationManager()
    return Self(
      getCurrentLocation: { try await LocationManager.shared.getLocation() }
    )
  }
}

extension LocationClient {
  final actor LocationManager {
    // I had to make a variable `shared` as `MainActor`, due to CLLocationManager.
    // ref: https://developer.apple.com/documentation/corelocation/cllocationmanager
    // It says that
    // "Core Location calls the methods of your delegate object using the RunLoop 3 of the thread on which you initialized CLLocationManager.
    // That thread must itself have an active RunLoop, like the one found in your app’s main thread."
    @MainActor static var shared = LocationManager()
    private let delegate: LocationDelegate = LocationDelegate()
    private let locationManager = CLLocationManager()
    
    func getLocation() async throws -> JournalLocation {
      locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
      locationManager.requestWhenInUseAuthorization()
      locationManager.delegate = delegate
      locationManager.requestLocation()
      
      var geocoder: CLGeocoder = .init()
      for await action in self.delegate {
        switch action {
        case .didGet(let location):
          print("location: \(location)")
          let placeMark = try await geocoder.reverseGeocodeLocation(location)
          let cityName = placeMark.last?.locality ?? "Unknown"
          return JournalLocation(
            latitude: location.coordinate.latitude,
            longitude: location.coordinate.longitude,
            cityName: cityName)
        case .didFailed(let error):
          print("error: \(error.localizedDescription)")
          throw error
        }
      }
      
      throw LocationError.locationNotAvailable
    }
    
  }
  
  // I made a `CLLocationManagerDelegate` use `AsyncSequence` to be used in `AsyncStream` on its caller.
  private final class LocationDelegate: NSObject, CLLocationManagerDelegate, AsyncSequence {
    //let callback: (Result<CLLocationCoordinate2D, LocationError>) -> Void
    typealias Element = Action
    
    private let continuation: AsyncStream<Element>.Continuation
    private let iterator: AsyncStream<Element>.Iterator
    
    override init() {
      //self.callback = callback
      let (stream, continuation) = AsyncStream.makeStream(of: Element.self)
      self.continuation = continuation
      self.iterator = stream.makeAsyncIterator()
    }
    
    func makeAsyncIterator() -> AsyncStream<Element>.AsyncIterator {
      iterator
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
      //if let location = locations.last?.coordinate {
      if let location = locations.last {
        //callback(.success(location))
        continuation.yield(.didGet(location))
      } else {
        //callback(.failure(.locationNotAvailable))
        continuation.yield(.didFailed(.locationNotAvailable))
      }
      
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
      
      if let clError = error as? CLError, clError.code == .denied {
        //callback(.failure(.permissionDenied))
        continuation.yield(.didFailed(.permissionDenied))
      } else {
        //callback(.failure(.locationNotAvailable))
        continuation.yield(.didFailed(.locationNotAvailable))
      }
    }
    
    // I removed `if locationServicesEnabled()` from main thread, and added the function below.
    // ref: https://stackoverflow.com/questions/25296691/get-users-current-location-coordinates/74915706#74915706
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
      switch manager.authorizationStatus {
      case .notDetermined:
        // Request the appropriate authorization based on the needs of the app
        manager.requestWhenInUseAuthorization()
        // manager.requestAlwaysAuthorization()
      case .restricted:
        continuation.yield(.didFailed(.permissionDenied))
        print("Sorry, restricted")
        // Optional: Offer to take user to app's settings screen
      case .denied:
        continuation.yield(.didFailed(.permissionDenied))
        print("Sorry, denied")
        // Optional: Offer to take user to app's settings screen
      case .authorizedAlways, .authorizedWhenInUse:
        // The app has permission so start getting location updates
        manager.startUpdatingLocation()
      @unknown default:
        continuation.yield(.didFailed(.locationNotAvailable))
        print("Unknown status")
      }
    }
  }
}



extension DependencyValues {
  var locationClient: LocationClient {
    get { self[LocationClient.self] }
    set { self[LocationClient.self] = newValue }
  }
}
