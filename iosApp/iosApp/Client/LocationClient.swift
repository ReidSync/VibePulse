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
    case didGet(CLLocationCoordinate2D)
    case didFailed(LocationError)
  }
  
  enum LocationError: Error {
    case permissionDenied
    case locationNotAvailable
  }
  
  var getCurrentLocation: @Sendable () async throws -> CLLocationCoordinate2D
}

extension LocationClient: DependencyKey {
  static var liveValue: Self {
    let locationManager = LocationManager()
    return Self(
      getCurrentLocation: { try await locationManager.getLocation() }
    )
  }
}

extension LocationClient {
  final actor LocationManager {
    private let delegate: LocationDelegate = LocationDelegate()
    private let locationManager = CLLocationManager()
    
    func getLocation() async throws -> CLLocationCoordinate2D {
      
      locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
      
      if CLLocationManager.locationServicesEnabled() {
        locationManager.requestWhenInUseAuthorization()
        locationManager.delegate = delegate
        locationManager.requestLocation()
        
        
        for await action in self.delegate {
          switch action {
          case .didGet(let location):
            print("location: \(location)")
            return location
          case .didFailed(let error):
            print("error: \(error.localizedDescription)")
            throw error
          }
        }
      } 
      else {
        throw LocationError.locationNotAvailable
      }
      
      throw LocationError.locationNotAvailable
    }
  }
  
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
      if let location = locations.last?.coordinate {
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
  }
}

extension DependencyValues {
  var locationClient: LocationClient {
    get { self[LocationClient.self] }
    set { self[LocationClient.self] = newValue }
  }
}
