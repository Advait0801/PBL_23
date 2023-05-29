import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/services/authentication.dart';

Authentication authObj = Authentication();
final _auth = FirebaseAuth.instance;

class landingPage extends StatelessWidget {
  const landingPage({super.key});

  @override
  Widget build(BuildContext context) {
    if (_auth.currentUser == null) {
      return Scaffold(
          appBar: AppBar(),
          body: Column(
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
          ));
    }
    return Scaffold(
      appBar: AppBar(
        title: Text("MediSwift"),
        leading: IconButton(onPressed: () {}, icon: Icon(Icons.circle)),
      ),
      body: SingleChildScrollView(
          child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            'Welcome!', // Greet the user with their name
            style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 32.0),
          ElevatedButton(
            onPressed: () {
              // Navigate to past diagnoses screen
              Get.toNamed('/pastDiagnosesPage');
            },
            child: Text('Past Diagnoses'),
          ),
          SizedBox(height: 16.0),
          ElevatedButton(
            onPressed: () {
              // Navigate to pending diagnoses screen
              Get.toNamed('/pendingDiagnosesPage');
            },
            child: Text('Pending Diagnoses'),
          ),
          SizedBox(height: 16.0),
          ElevatedButton(
            onPressed: () async {
              //print(authObj.getUserUid(context: context));
              // Navigate to pending diagnoses screen
              await dbObj.addListEntry(
                  duid: 'zmnB2D2F05c1ne6XzyMS',
                  puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
                  context: context);
              await dbObj.addListEntry(
                  duid: 'xDHHmeZwCsw5TExPL049',
                  puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
                  context: context);
              await dbObj.addListEntry(
                  duid: 'wVcd0CpH0kNTL57fMB7r',
                  puid: 'usXQUGH8ojR6wxHXL6ypIEOF8dF2',
                  context: context);
            },
            child: Text('add list entry'),
          ),
        ],
      )),
    );
  }
}
