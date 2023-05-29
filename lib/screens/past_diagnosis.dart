import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/services/authentication.dart';
import 'package:pbl/services/database.dart';

database databaseObj = database();

class pastDiagnosesPage extends StatefulWidget {
  const pastDiagnosesPage({super.key});

  @override
  State<pastDiagnosesPage> createState() => _pastDiagnosesPageState();
}

class _pastDiagnosesPageState extends State<pastDiagnosesPage> {
  List<String> patientUids = [];
  List<String> diagnosisUids = [];
  List<String> patientNames = [];
  List<String> timeStamps = [];

  @override
  initState() {
    super.initState();
    print("initState Called");
    setListValues();
    //patientUids = await databaseObj.getPatientUids(context: context, diagnosesType: 'pastDiagnosis');
  }

  Future<void> setListValues() async {
    List<String> res1 = await databaseObj.getPatientUids(
        context: context, diagnosesType: 'pastDiagnoses');
    List<String> res2 = await databaseObj.getDiagnosisUids(
        context: context, diagnosesType: 'pastDiagnoses');
    List<String> res3 = [];
    List<String> res4 = [];

    for (String useString in res1) {
      String patientName = await databaseObj.getPatientName(
          userUid: useString, context: context);
      res3.add(patientName);
    }
    int counter = 0;
    for (String useString in res2) {
      String timestamp = await databaseObj.getTimestamp(
          diagnosisUid: useString, userUid: res1[counter], context: context);
      res4.add(timestamp);
      counter++;
    }

    setState(() {
      patientUids = res1;
      diagnosisUids = res2;
      patientNames = res3;
      timeStamps = res4;
    });
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(itemBuilder: (BuildContext context, int index) {
      return ListTile(
        title: Text('Patient: ' + patientNames[index]),
        subtitle: Text('At: ' + timeStamps[index]),
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => EachDiagnosis(
                    diagnosisUid: diagnosisUids[index],
                    userUid: patientUids[index])),
          );
        },
      );
    });
  }
}
