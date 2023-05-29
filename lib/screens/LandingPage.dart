import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/services/authentication.dart';
import 'package:pbl/services/database.dart';

Authentication authObj = Authentication();
final _auth = FirebaseAuth.instance;
database dbObj = database();

class landingPage extends StatelessWidget {
  const landingPage({super.key});

  @override
  Widget build(BuildContext context) {
    if (_auth.currentUser == null) {
      return Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          FilledButton(
              onPressed: () {
                //Get.to Sign up page
                Get.toNamed('/signUpPage');
              },
              child: Text('Sign up')),
          SizedBox(height: 20),
          FilledButton(
              onPressed: () async {
                Get.toNamed('/logInPage');
              },
              child: Text('Log in')),
        ],
      );
    }
    return FutureBuilder<Map<String, dynamic>>(
      future: dbObj.getMyData(context: context),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (snapshot.hasData) {
          Map<String, dynamic> userData = snapshot.data!;
          String userName = userData['name'];

          // Replace with the actual number of pending and completed diagnoses
          int pendingDiagnoses = 5;
          int completedDiagnoses = 10;

          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                'Welcome, $userName!',
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 16),
              Image.asset(
                'assets/doctorPic.png',
                height: 200,
                width: 200,
                fit: BoxFit.cover,
              ),
              SizedBox(height: 32),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Column(
                    children: [
                      Text(
                        'Pending Diagnoses',
                        style: TextStyle(fontSize: 16),
                      ),
                      SizedBox(height: 8),
                      Text(
                        '$pendingDiagnoses',
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          // Navigate to the Pending Diagnoses Page
                        },
                        child: Text('View Pending Diagnoses'),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        'Diagnoses Completed',
                        style: TextStyle(fontSize: 16),
                      ),
                      SizedBox(height: 8),
                      Text(
                        '$completedDiagnoses',
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          // Navigate to the Completed Diagnoses Page
                        },
                        child: Text('View Completed Diagnoses'),
                      ),
                    ],
                  ),
                ],
              ),
            ],
          );
        } else {
          return Center(
            child: Text('Failed to load user data.'),
          );
        }
      },
    );
    // return SingleChildScrollView(
    //     child: Column(
    //   mainAxisAlignment: MainAxisAlignment.center,
    //   crossAxisAlignment: CrossAxisAlignment.center,
    //   mainAxisSize: MainAxisSize.min,
    //   children: [
    //     Text(
    //       'Welcome!', // Greet the user with their name
    //       style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
    //     ),
    //     SizedBox(height: 32.0),
    //     ElevatedButton(
    //       onPressed: () {
    //         // Navigate to past diagnoses screen
    //         Get.toNamed('/pastDiagnosesPage');
    //       },
    //       child: Text('Past Diagnoses'),
    //     ),
    //     SizedBox(height: 16.0),
    //     ElevatedButton(
    //       onPressed: () {
    //         // Navigate to pending diagnoses screen
    //         Get.toNamed('/pendingDiagnosesPage');
    //       },
    //       child: Text('Pending Diagnoses'),
    //     ),
    //     SizedBox(height: 16.0),
    //     ElevatedButton(
    //       onPressed: () async {
    //         //print(authObj.getUserUid(context: context));
    //         // Navigate to pending diagnoses screen
    //         await dbObj.addListEntry(
    //             duid: 'zmnB2D2F05c1ne6XzyMS',
    //             puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
    //             context: context);
    //         await dbObj.addListEntry(
    //             duid: 'xDHHmeZwCsw5TExPL049',
    //             puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
    //             context: context);
    //         await dbObj.addListEntry(
    //             duid: 'wVcd0CpH0kNTL57fMB7r',
    //             puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
    //             context: context);
    //       },
    //       child: Text('add list entry'),
    //     ),
    //   ],
    // ));
    // ;
  }
}
