import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/services/authentication.dart';
import 'package:pbl/services/database.dart';

database dbObj = database();

class MyProfilePage extends StatelessWidget {
  const MyProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Map<String, dynamic>>(
      future: dbObj.getMyData(context: context),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (snapshot.hasData) {
          Map<String, dynamic> userData = snapshot.data!;
          return ListView(
            padding: EdgeInsets.all(16.0),
            children: [
              ListTile(
                title: Text('Name:'),
                subtitle: Text(userData['name']),
              ),
              ListTile(
                title: Text('Age:'),
                subtitle: Text(userData['age'].toString()),
              ),
              ListTile(
                title: Text('Gender:'),
                subtitle: Text(userData['gender']),
              ),
              ListTile(
                title: Text('Location:'),
                subtitle: Text(userData['location']),
              ),
              ListTile(
                title: Text('Qualification:'),
                subtitle: Text(userData['degree']),
              ),
              ListTile(
                title: Text('Speciality:'),
                subtitle: Text(userData['speciality']),
              ),
              ListTile(
                title: Text('Phone Number'),
                subtitle: Text(userData['phoneNumber']),
              ),
              ListTile(
                title: Text('Email id:'),
                subtitle: Text(userData['emailid']),
              ),
            ],
          );
        } else {
          return Center(
            child: Text('Failed to load user information.'),
          );
        }
      },
    );
  }
}
