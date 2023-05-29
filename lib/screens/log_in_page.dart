import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:pbl/services/authentication.dart';

Authentication authObj = Authentication();

class LogInPage extends StatelessWidget {
  TextEditingController emailCtr = TextEditingController();
  TextEditingController passwordCtr = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
            onPressed: () {
              Get.toNamed('/');
            },
            icon: Icon(Icons.home)),
        title: Text('Login'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: emailCtr,
              decoration: InputDecoration(
                labelText: 'Email',
              ),
            ),
            SizedBox(height: 16.0),
            TextField(
              controller: passwordCtr,
              obscureText: true,
              decoration: InputDecoration(
                labelText: 'Password',
              ),
            ),
            SizedBox(height: 32.0),
            ElevatedButton(
              onPressed: () async {
                await authObj.logInWithEmail(
                    email: emailCtr.text,
                    password: passwordCtr.text,
                    context: context);
              },
              child: Text('Log In'),
            ),
            SizedBox(height: 16.0),
            ElevatedButton(
              onPressed: () {
                // Navigate to sign-up screen
              },
              child: Text('Sign up'),
            ),
            SizedBox(height: 8.0),
            Text('Creat a new account instead'),
          ],
        ),
      ),
    );
  }
}
