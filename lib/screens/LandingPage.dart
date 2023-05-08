import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
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
                  },
                  child: Text('Sign up')),
              SizedBox(height: 20),
              FilledButton(
                  onPressed: () {
                    //Get.to Log in page
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
        children: [Text('Mediswift')],
      )),
    );
  }
}
