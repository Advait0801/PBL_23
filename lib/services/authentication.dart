import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:pbl/global/widgets.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/services/database.dart';
// import 'package:project_hive/globals/widgets.dart';
// import 'package:project_hive/models/company_employee_model.dart';
// import 'package:project_hive/models/independent_user.dart';
// import 'package:project_hive/models/institute_faculty_model.dart';
// import 'package:project_hive/models/student_model.dart';
// import 'package:project_hive/services/database.dart';

final _database = database();

class Authentication {
  final _auth = FirebaseAuth.instance;

  Future<void> signUpWithEmail({
    required String email,
    required String password,
    required BuildContext context,
    required UserModel userModel,
  }) async {
    try {
      await _auth.createUserWithEmailAndPassword(
          email: email, password: password);
      await sendVerificationMail(context);

      if (_auth.currentUser == null) {
        throw Exception("User is null");
      } else {
        String sendUid = _auth.currentUser!.uid;
        _database.createUserRecord(
            useUid: sendUid, context: context, userModel: userModel);
      }
    } on FirebaseAuthException catch (e) {
      print(e);
      showSnackBar(context, e.toString());
    } /*catch{
      showSnackBar(context, "Oops! something went wrong :/");
    }*/
    catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  Future<void> sendVerificationMail(BuildContext context) async {
    try {
      await _auth.currentUser!.sendEmailVerification();
    } on FirebaseAuthException catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  Future<void> logInWithEmail({
    required String email,
    required String password,
    required BuildContext context,
  }) async {
    try {
      await _auth.signInWithEmailAndPassword(email: email, password: password);
      if (_auth.currentUser!.emailVerified) {
        print('logged in as $email');
        //Get.to landing page
      } else {
        //Get.to "please verify account" page
        signOut(context: context);
      }
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  Future<void> signOut({
    required BuildContext context,
  }) async {
    try {
      await _auth.signOut();
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  Future<String> getUserUid({required BuildContext context}) async {
    try {
      String useString = '';
      if (_auth.currentUser == null) {
        throw Exception('User is null');
      } else {
        useString = _auth.currentUser!.uid;
      }
      return useString;
    } catch (e) {
      showSnackBar(context, e.toString());
    }
    return 'invalid uid';
  }

  Future<bool> getUserStatus({required BuildContext context}) async {
      if (_auth.currentUser == null) {
        return false;
      } else {
        return true;
      }
  }
}
