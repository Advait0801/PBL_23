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

  Future<String> getPatientName({
    required String userUid,
    required BuildContext context,
  }) async {
    try {
      DocumentSnapshot docSnap = await _firestore.doc("users/$userUid").get();
      if (docSnap.exists) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        String useString = doc['firstName'] + doc['lastName'];
        return useString;
      } else {
        throw Exception("Patient not found");
      }
    } catch (e) {
      showSnackBar(context, e.toString());
      return 'Error';
    }
  }

  Future<Map<String, dynamic>> getPatientData({
    required String userUid,
    required BuildContext context,
  }) async {
    try {
      DocumentSnapshot docSnap = await _firestore.doc("users/$userUid").get();
      if (docSnap.exists) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        //String useString = doc['firstName'] + doc['lastName'];
        return doc;
      } else {
        throw Exception("Patient not found");
      }
    } catch (e) {
      showSnackBar(context, e.toString());
      return {};
    }
  }

  Future<String> getTimestamp({
    required String diagnosisUid,
    required String userUid,
    required BuildContext context,
  }) async {
    try {
      DocumentSnapshot docSnap =
          await _firestore.doc("users/$userUid/diagnoses/$diagnosisUid").get();
      if (docSnap.exists) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        String useString = '';
        if (doc.containsKey('time') && doc['time'] != null) {
          useString = doc['time'].toString();
        }
        return useString;
      } else {
        throw Exception("Patient or Diagnosis not found");
      }
    } catch (e) {
      showSnackBar(context, e.toString());
      return 'Error';
    }
  }

  Future<void> submitDiagnosisReport(
      {required String diagnosisUid,
      required BuildContext context,
      required String content,
      required String patientUid}) async {
    try {
      await _firestore
          .doc("users/$patientUid/diagnosis/$diagnosisUid/report")
          .update({'content': content});
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

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

  Future<List<String>> getPatientUids(
      {required BuildContext context, required String diagnosesType}) async {
    try {
      String userUid = await _auth.getUserUid(context: context);
      DocumentSnapshot docSnap =
          await _firestore.collection('doctors').doc(userUid).get();
      if (docSnap.exists /*&& docSnap.data()!.containsKey(diagnosesType)*/) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        List<dynamic> useList = [];
        if (doc.containsKey('$diagnosesType') && doc[diagnosesType] != null) {
          useList = doc[diagnosesType];
        }

        List<Map<String, String>> serializedList = useList
            .map<Map<String, String>>(
              (dynamic element) => Map<String, String>.from(element),
            )
            .toList();

        List<String> result = [];
        for (Map<String, String> theMap in serializedList) {
          if (theMap.isNotEmpty) {
            result.add(theMap['patientUid']!);
          }
        }
        return result;
      }
      return [];
    } catch (e) {
      showSnackBar(context, e.toString());
      return [];
    }
  }

  // Future<void> addListEntry({
  //   required String duid,
  //   required String puid,
  //   required BuildContext context,
  // }) async {
  //   try {
  //     String muid = await _auth.getUserUid(context: context);
  //     DocumentSnapshot docSnap = await _firestore.doc('doctors/$muid').get();
  //     if (docSnap.exists) {
  //       Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
  //       if (doc.containsKey('pendingDiagnoses')) {
  //         List<List<String>> useList = [];
  //         if (doc['pendingDiagnoses'] != null) {
  //           useList = doc['pendingDiagnoses'];
  //         }
  //         List<String> addList = [puid, duid];
  //         useList.add(addList);
  //         await _firestore
  //             .doc('doctors/$muid')
  //             .update({'pendingDiagnoses': useList});
  //       } else
  //         throw Exception('Pending Diagnoses field does not exist');
  //       print('aaaaaaaaaaaaaaaaaaaaaaaaaa');
  //     } else {
  //       throw Exception('Document not found');
  //     }
  //   } catch (e) {
  //     showSnackBar(context, e.toString());
  //   }
  // }

  /*Future<void> addListEntry({
    required String duid,
    required String puid,
    required BuildContext context,
  }) async {
    try {
      String muid = await _auth.getUserUid(context: context);
      DocumentSnapshot docSnap = await _firestore.doc('doctors/$muid').get();
      if (docSnap.exists) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        if (doc.containsKey('pendingDiagnoses')) {
          List<Map<String, String>> useList = [];
          if (doc['pendingDiagnoses'] != null) {
            // List<dynamic> pendingDiagnoses = doc['pendingDiagnoses'];
            // useList = pendingDiagnoses.map<List<String>>((dynamic element) {
            //   List<dynamic> innerList = element;
            //   return innerList.cast<String>();
            // }).toList();
            useList = doc['pendingDiagnoses'];
          }
          //List<String> addList = [puid, duid];
          Map<String, String> addMap = {
            'patientUid': puid,
            'diagnosisUid': duid,
          };
          useList.add(addMap);

          // List<List<dynamic>> serializedList = useList
          //     .map<List<dynamic>>(
          //       (List<String> innerList) =>
          //           innerList.map<dynamic>((String value) => value).toList(),
          //     )
          //     .toList();

          await _firestore
              .doc('doctors/$muid')
              .update({'pendingDiagnoses': useList});
        } else {
          throw Exception('Pending Diagnoses field does not exist');
        }
        print('List entry added successfully');
      } else {
        throw Exception('Document not found');
      }
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }*/

  Future<void> addListEntry({
    required String duid,
    required String puid,
    required BuildContext context,
  }) async {
    try {
      String muid = await _auth.getUserUid(context: context);
      DocumentSnapshot docSnap = await _firestore.doc('doctors/$muid').get();
      if (docSnap.exists) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        if (doc.containsKey('pendingDiagnoses')) {
          List<dynamic> useList = [];
          if (doc['pendingDiagnoses'] != null) {
            useList = doc['pendingDiagnoses'];
          }
          Map<String, String> addMap = {
            'patientUid': puid,
            'diagnosisUid': duid,
          };
          useList.add(addMap);

          List<Map<String, String>> serializedList = useList
              .map<Map<String, String>>(
                (dynamic element) => Map<String, String>.from(element),
              )
              .toList();

          await _firestore
              .doc('doctors/$muid')
              .update({'pendingDiagnoses': serializedList});
        } else {
          throw Exception('Pending Diagnoses field does not exist');
        }
        print('List entry added successfully');
      } else {
        throw Exception('Document not found');
      }
    } catch (e) {
      showSnackBar(context, e.toString());
    }
  }

  Future<List<String>> getDiagnosisUids(
      {required BuildContext context, required String diagnosesType}) async {
    try {
      String userUid = await _auth.getUserUid(context: context);
      DocumentSnapshot docSnap =
          await _firestore.collection('doctors').doc(userUid).get();
      if (docSnap.exists /*&& docSnap.data()!.containsKey(diagnosesType)*/) {
        Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
        List<dynamic> useList = [];
        if (doc.containsKey('$diagnosesType') && doc[diagnosesType] != null) {
          useList = doc[diagnosesType];
        }

        List<Map<String, String>> serializedList = useList
            .map<Map<String, String>>(
              (dynamic element) => Map<String, String>.from(element),
            )
            .toList();

        List<String> result = [];
        for (Map<String, String> theMap in serializedList) {
          if (theMap.isNotEmpty) {
            result.add(theMap['diagnosisUid']!);
          }
        }
        return result;
      }
      return [];
    } catch (e) {
      showSnackBar(context, e.toString());
      return [];
    }
  }

//   Future<List<String>> getDiagnosesUids(
//       {required BuildContext context, required String diagnosesType}) async {
//     try {
//       String userUid = await _auth.getUserUid(context: context);
//       DocumentSnapshot docSnap =
//           await _firestore.collection('doctors').doc(userUid).get();
//       if (docSnap.exists /*&& docSnap.data()!.containsKey(diagnosesType)*/) {
//         Map<String, dynamic> doc = docSnap.data() as Map<String, dynamic>;
//         List<List<String>> useList = doc[diagnosesType];
//         List<String> result = [];
//         for (List<String> theList in useList) {
//           if (theList.length > 1) {
//             result.add(theList[1]);
//           }
//         }
//         return result;
//       }
//       return [];
//     } catch (e) {
//       showSnackBar(context, e.toString());
//       return [];
//     }
//   }
}
