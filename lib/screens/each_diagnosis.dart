import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/pending_diagnosis.dart';
import 'package:pbl/services/authentication.dart';

import '../services/database.dart';

database dbObj = database();

class EachDiagnosis extends StatefulWidget {
  String diagnosisUid;
  String userUid;
  EachDiagnosis({super.key, required this.diagnosisUid, required this.userUid});

  @override
  State<EachDiagnosis> createState() => _EachDiagnosisState();
}

class _EachDiagnosisState extends State<EachDiagnosis> {
  // @override
  // initState() {
  //   super.initState();
  //   //print("initState Called");
  // }

  @override
  Widget build(BuildContext context) {
    TextEditingController reportCtr = TextEditingController();
    return Scaffold(
      appBar: AppBar(
          leading: IconButton(
              onPressed: () {
                Get.toNamed('/');
              },
              icon: Icon(Icons.home)),
          title: Text('Diagnosis')),
      floatingActionButton: FloatingActionButton(onPressed: () {
        showBottomSheet(
            context: context,
            builder: (_) {
              return Container(
                  height: MediaQuery.of(context).size.height / 2,
                  width: double.infinity,
                  color: Colors.blue,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      TextFormField(
                        controller: reportCtr,
                      ),
                      ElevatedButton(
                          onPressed: () async {
                            await dbObj.submitDiagnosisReport(
                                diagnosisUid: widget.diagnosisUid,
                                context: context,
                                content: reportCtr.text,
                                patientUid: widget.userUid);
                          },
                          child: Text('Submit'))
                    ],
                  ));
            });
      }),
      body: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          FutureBuilder(
              future: dbObj.getPatientName(
                  userUid: widget.userUid, context: context),
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (snapshot.hasData) {
                    String patientName = snapshot.data.toString();
                    return Row(children: [
                      Text('Patient name: '),
                      Text(patientName),
                    ]);
                  } else {
                    return Text('Error Fetching Patient Name');
                  }
                }
                return CircularProgressIndicator();
              }),
          SizedBox(height: 15),
          Text('Patient Info:'),
          FutureBuilder(
              future: dbObj.getPatientData(
                  userUid: widget.userUid, context: context),
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (snapshot.hasData) {
                    //String patientName = snapshot.data.toString();
                    Map<String, dynamic> useMap = snapshot.data!;
                    return Column(
                      children: [
                        Row(children: [
                          Text('Patient name: '),
                          Text(useMap['firstName'] + useMap['lastName']),
                        ]),
                        Row(children: [
                          Text('DOB:  '),
                          Text(useMap['dateOfBirth']),
                        ]),
                        Row(children: [
                          Text("Sex:  "),
                          Text(useMap['gender']),
                        ]),
                        Row(children: [
                          Text('Blood Group:  '),
                          Text(useMap['bldGrp']),
                        ]),
                        Row(children: [
                          Text('Height:  '),
                          Text(useMap['height']),
                        ]),
                        Row(children: [
                          Text('Weight:  '),
                          Text(useMap['weight']),
                        ]),
                      ],
                    );
                  } else {
                    return Text('Error Fetching Patient Data');
                  }
                }
                return CircularProgressIndicator();
              }),
          SizedBox(height: 15),
          Text('Symptoms:'),
          SizedBox(height: 15),
          StreamBuilder<QuerySnapshot>(
            stream: FirebaseFirestore.instance
                .collection('users')
                .doc(widget.userUid)
                .collection('diagnoses')
                .doc(widget.diagnosisUid)
                .collection('symptoms')
                .snapshots(),
            builder:
                (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
              if (snapshot.hasError) {
                return Text('Error: ${snapshot.error}');
              }

              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(child: CircularProgressIndicator());
              }

              if (snapshot.hasData) {
                List<QueryDocumentSnapshot> documents = snapshot.data!.docs;
                return SizedBox(
                  height: 200,
                  child: ListView.builder(
                    itemCount: documents.length,
                    itemBuilder: (BuildContext context, int index) {
                      Map<String, dynamic> data =
                          documents[index].data() as Map<String, dynamic>;

                      String attributeValue = data['name'] as String;
                      print(attributeValue);

                      return ListTile(
                        title: Text("$index" + "  " + "$attributeValue"),
                      );
                    },
                  ),
                );
              }

              return Center(child: Text('No documents found'));
            },
          ),
          SizedBox(height: 15),
          Text('Predictions:'),
          SizedBox(height: 15),
          StreamBuilder<QuerySnapshot>(
            stream: FirebaseFirestore.instance
                .collection('users')
                .doc(widget.userUid)
                .collection('diagnoses')
                .doc(widget.diagnosisUid)
                .collection('predictions')
                .snapshots(),
            builder:
                (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
              if (snapshot.hasError) {
                return Text('Error: ${snapshot.error}');
              }

              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(child: CircularProgressIndicator());
              }

              if (snapshot.hasData) {
                List<QueryDocumentSnapshot> documents = snapshot.data!.docs;
                return SizedBox(
                  height: 200,
                  child: ListView.builder(
                    itemCount: documents.length,
                    itemBuilder: (BuildContext context, int index) {
                      Map<String, dynamic> data =
                          documents[index].data() as Map<String, dynamic>;

                      // Replace 'attributeName' with the attribute name you want to display
                      String attributeValue = data['name'] as String;

                      return ListTile(
                        title: Text("$index" + "  " + "$attributeValue"),
                      );
                    },
                  ),
                );
              }

              return Center(child: Text('No documents found'));
            },
          ),
        ],
      ),
    );
  }
}
