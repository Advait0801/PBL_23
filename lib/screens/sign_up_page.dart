import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/services/authentication.dart';

Authentication authObj = Authentication();

class SignUpPage extends StatelessWidget {
  final TextEditingController firstNameController = TextEditingController();
  final TextEditingController lastNameController = TextEditingController();
  final TextEditingController ageController = TextEditingController();
  final TextEditingController genderController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController locationController = TextEditingController();
  final TextEditingController phoneNumberController = TextEditingController();
  final TextEditingController qualificationController = TextEditingController();
  final TextEditingController specialityController = TextEditingController();
  final TextEditingController descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
            onPressed: () {
              Get.toNamed('/');
            },
            icon: Icon(Icons.home)),
        title: Text('Sign Up'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: firstNameController,
                decoration: InputDecoration(
                  labelText: 'First Name',
                ),
              ),
              TextField(
                controller: lastNameController,
                decoration: InputDecoration(
                  labelText: 'Last Name',
                ),
              ),
              TextField(
                controller: ageController,
                decoration: InputDecoration(
                  labelText: 'Age',
                ),
              ),
              TextField(
                controller: genderController,
                decoration: InputDecoration(
                  labelText: 'Gender',
                ),
              ),
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: 'Email',
                ),
              ),
              TextField(
                controller: passwordController,
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Password',
                ),
              ),
              TextField(
                controller: locationController,
                decoration: InputDecoration(
                  labelText: 'Location',
                ),
              ),
              TextField(
                controller: phoneNumberController,
                decoration: InputDecoration(
                  labelText: 'Phone Number',
                ),
              ),
              TextField(
                controller: qualificationController,
                decoration: InputDecoration(
                  labelText: 'Qualification',
                ),
              ),
              TextField(
                controller: specialityController,
                decoration: InputDecoration(
                  labelText: 'Speciality',
                ),
              ),
              TextField(
                controller: descriptionController,
                decoration: InputDecoration(
                  labelText: 'Description',
                ),
              ),
              SizedBox(height: 32.0),
              ElevatedButton(
                onPressed: () {
                  authObj.signUpWithEmail(
                      email: emailController.text,
                      password: passwordController.text,
                      context: context,
                      userModel: UserModel(
                        age: int.parse(ageController.text),
                        emailid: emailController.text,
                        speciality: specialityController.text,
                        name:
                            firstNameController.text + lastNameController.text,
                        description: descriptionController.text,
                        gender: genderController.text,
                        degree: qualificationController.text,
                        location: locationController.text,
                        phoneNumber: phoneNumberController.text,
                      ));
                  //Get.to Sign up page
                },
                child: Text('Sign Up'),
              ),
              SizedBox(height: 16.0),
              TextButton(
                onPressed: () {
                  // Navigate to login screen
                },
                child: Text('Log in instead'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
