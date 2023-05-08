import 'dart:convert';

class UserModel {
  int age;
  String? description;
  final String emailid;
  String? gender;
  String? degree;
  String speciality;
  String? location;
  String name;
  String? phoneNumber;
  String? profilePhoto;
  String? uid;
  int? rating;

  UserModel(
      {required this.age,
      required this.emailid,
      required this.speciality,
      required this.name,
      this.uid,
      this.description,
      this.gender,
      this.degree,
      this.location,
      this.phoneNumber,
      this.profilePhoto,
      this.rating});

  Map<String, dynamic> toMap() {
    return {
      'age': age,
      'description': description,
      'emailid': emailid,
      'gender': gender,
      'degree': degree,
      'speciality': speciality,
      'location': location,
      'name': name,
      'phoneNumber': phoneNumber,
      'uid': uid,
      'profilePhoto': profilePhoto,
      'rating': rating,
    };
  }

  factory UserModel.fromMap(Map<String, dynamic> map) {
    return UserModel(
      age: map['age'] ?? 0,
      description: map['description'] ?? '',
      emailid: map['emailid'] ?? '',
      gender: map['gender'] ?? '',
      degree: map['degree'] ?? '',
      speciality: map['speciality'] ?? '',
      location: map['location'] ?? '',
      name: map['name'] ?? '',
      phoneNumber: map['phoneNumber'] ?? '',
      profilePhoto: map['profilePhoto'] ?? '',
      uid: map['uid'] ?? '',
      rating: map['rating'],
    );
  }

  String toJson() => jsonEncode(toMap());

  factory UserModel.fromJson(String source) {
    return UserModel.fromMap(json.decode(source));
  }
}
