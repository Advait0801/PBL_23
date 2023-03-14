//
//  EndlessMedicalAPI.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 08/03/23.
//

import Foundation

class SymptomAnalyser {
    
    var symptoms: [Symptom]
    
    let baseURL = "https://api.endlessmedical.com/v1/dx/"
    
    func getSessionID() async throws -> String {
        guard let url = URL(string: baseURL + "InitSession") else { throw SymptomAnalyzerError.invalidURL }
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let decodedData = try JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] == "ok" {
            guard let sessionID = decodedData["SessionID"] else { throw SymptomAnalyzerError.badResponse }
            return sessionID
        } else {
            throw SymptomAnalyzerError.badResponse
        }
    }
    
    func acceptTermsOfUse(sessionID: String) async throws {
        let passphrase = "I have read, understood and I accept and agree to comply with the Terms of Use of EndlessMedicalAPI and Endless Medical services. The Terms of Use are available on endlessmedical.com"
        guard var url = URL(string: baseURL + "AcceptTermsOfUse") else { throw SymptomAnalyzerError.invalidURL }
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
            URLQueryItem(name: "passphrase", value: passphrase),
        ]
        
        url.append(queryItems: queryItems)
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        guard let (data, _) = try? await URLSession.shared.upload(for: request, from: Data()) else { throw SymptomAnalyzerError.termsNotAccepted }
        guard let decodedData = try? JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] != "ok" {
            throw SymptomAnalyzerError.badResponse
        }
    }
    
    func uploadSymptom(sessionID: String, symptomName: String, value: String) async throws {
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
            URLQueryItem(name: "name", value: symptomName),
            URLQueryItem(name: "value", value: value)
        ]
        guard var url = URL(string: baseURL + "UpdateFeature") else { throw SymptomAnalyzerError.invalidURL }
        url.append(queryItems: queryItems)
        
        url.append(queryItems: queryItems)
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        guard let (data, _) = try? await URLSession.shared.upload(for: request, from: Data()) else { throw SymptomAnalyzerError.featureNotUpdated }
        guard let decodedData = try JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] != "ok" {
            throw SymptomAnalyzerError.featureNotUpdated
        }
    }
    
    func analyze(sessionID: String) async throws {
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
        ]
        guard var url = URL(string: baseURL + "Analyze") else { throw SymptomAnalyzerError.invalidURL }
        url.append(queryItems: queryItems)
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let decodedData = try JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : AnyObject] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] as? String != "ok" {
            throw SymptomAnalyzerError.analysisFailed
        }
        print(decodedData)
    }
    
    func getDiagnosis(symptoms: [Symptom : Double]) async {
        do {
            let sessionID = try await getSessionID()
            try await acceptTermsOfUse(sessionID: sessionID)
            for pair in symptoms {
                let value = String(format: pair.key.type == .double ? "%.2f" : "%.0f", pair.value)
                try await uploadSymptom(sessionID: sessionID, symptomName: pair.key.name, value: value)
            }
            try await analyze(sessionID: sessionID)
        } catch {
            print(error)
        }
    }
    
    init() {
        let data: Data
        let filename = "SymptomsOutput.json"
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil) else {
            fatalError("Couldn't find \(filename) in main bundle.")
        }
        
        do {
            data = try Data(contentsOf: file)
        } catch {
            fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
        }
        
        do {
            let decoder = JSONDecoder()
            self.symptoms = try decoder.decode(Symptoms.self, from: data)
        } catch {
            fatalError("Couldn't parse \(filename) as \(Symptoms.self): \n\(error)")
        }
    }
}

enum SymptomAnalyzerError: Error, CustomStringConvertible {
    case invalidURL
    case badResponse
    case termsNotAccepted
    case featureNotUpdated
    case analysisFailed
    
    var description: String {
        switch self {
            case .invalidURL: return "invalidURL"
            case .badResponse: return "badResponse"
            case .termsNotAccepted: return "termsNotAccepted"
            case .featureNotUpdated: return "featureNotUpdated"
            case .analysisFailed: return "analysisFailed"
        }
    }
}
