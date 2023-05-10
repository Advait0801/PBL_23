import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/services/authentication.dart';

final databaseObj = FirebaseFirestore.instance;

class pendingDiagnosesPage extends StatelessWidget {
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
          stream: databaseObj.collection('users').doc(),
          builder: (context, snapshot){
            if(!snapshot.hasData){
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
            return Center(child: CircularProgressIndicator(),);
          })
        );
  }
}
