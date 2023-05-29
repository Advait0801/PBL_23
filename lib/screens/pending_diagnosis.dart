import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/services/authentication.dart';
import 'package:pbl/services/database.dart';

//final databaseObj = FirebaseFirestore.instance;
database databaseObj = database();

/*class pendingDiagnosesPage extends StatelessWidget {
  const pendingDiagnosesPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        /*body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ListView.builder(itemBuilder: itemBuilder)
          ],
        ),
      ),*/
        body: StreamBuilder<QuerySnapshot>(
          stream: databaseObj.collection('users').snapshots(),
          builder: (context, snapshot){

            List<List<String>> useList = await databaseObj.getDiagnosisList();

            if(snapshot.connectionState == ConnectionState.waiting){
              return CircularProgressIndicator();
            }if(snapshot.connectionState == ConnectionState.active){if(!snapshot.hasData){
              return Center(child: Text('Unable to fetch data'));
            }
            if(snapshot.hasData && snapshot.data.length == 0){
              return Center(child: Text('No pending diagnoses'));
            }
            if(snapshot.hasData){
              return ListView.builder(
          itemCount: snapshot.data.length,
          itemBuilder: (BuildContext context, int index) {

            return ListTile(
              onTap: (){},
              title: ,
              subtitle: ,
              leading: Icon(Icons.pages),
              
            );
          });
            }
          }
          print("Connection state neither active, nor waiting");
            return Center(child: Text("Something went wrong :/"),);
          })
        );
  }
}
*/

class pendingDiagnosesPage extends StatefulWidget {
  const pendingDiagnosesPage({super.key});

  @override
  State<pendingDiagnosesPage> createState() => _pendingDiagnosesPageState();
}

class _pendingDiagnosesPageState extends State<pendingDiagnosesPage> {
  List<String> patientUids = [];
  List<String> diagnosisUids = [];
  List<String> patientNames = [];
  List<String> timeStamps = [];

  @override
  initState() {
    super.initState();
    print("initState Called");
    setListValues();
    //patientUids = await databaseObj.getPatientUids(context: context, diagnosesType: 'pendingDiagnoses');
  }

  Future<void> setListValues() async {
    List<String> res1 = await databaseObj.getPatientUids(
        context: context, diagnosesType: 'pendingDiagnoses');
    List<String> res2 = await databaseObj.getDiagnosisUids(
        context: context, diagnosesType: 'pendingDiagnoses');
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
    print('came to pending diagnoses page');
    return /*SingleChildScrollView(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [],
      ),
    );*/
        Scaffold(
      appBar: AppBar(
        leading: IconButton(
            onPressed: () {
              Get.toNamed('/');
            },
            icon: Icon(Icons.home)),
        title: Text('Pending Diagnoses'),
      ),
      body: ListView.builder(itemBuilder: (BuildContext context, int index) {
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
      }),
    );
  }
}
