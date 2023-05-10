import 'dart:developer';

import 'dart:convert';
//import 'dart:js_interop';

import 'package:flutter/material.dart';
import 'package:pbl/global/widgets.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/services/authentication.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
// import 'package:flutter/material.dart';
// import 'package:project_hive/globals/widgets.dart';
// import 'package:project_hive/models/application_model.dart';
// import 'package:project_hive/models/company_employee_model.dart';
// import 'package:project_hive/models/independent_user.dart';
// import 'package:project_hive/models/institute_faculty_model.dart';
// import 'package:project_hive/models/project_model.dart';
// import 'package:project_hive/models/student_model.dart';
// import 'package:project_hive/services/authentication.dart';
// import 'package:uuid/uuid.dart';

final _auth = Authentication();

class database {
  //Authentication _auth = Authentication();
  final _firestore = FirebaseFirestore.instance;

  //create user
  Future<void> createUserRecord({
    required String useUid,
    required BuildContext context,
    required UserModel userModel,
  }) async {
    userModel.uid = useUid;
    try {
      await _firestore.doc("doctors/$useUid").set(userModel.toMap());
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  //update user
  Future<void> updateUserRecord({
    required String useUid,
    required BuildContext context,
    required UserModel userModel,
  }) async {
    try {
      await _firestore.doc("doctors/$useUid").update(userModel.toMap());
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

}
