import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/models/user_model.dart';
import 'package:pbl/screens/LandingPage.dart';
import 'package:pbl/screens/each_diagnosis.dart';
import 'package:pbl/screens/log_in_page.dart';
import 'package:pbl/screens/my_profile_page.dart';
import 'package:pbl/screens/past_diagnosis.dart';
import 'package:pbl/screens/pending_diagnosis.dart';

class MyHomePage extends StatefulWidget {
  final int selectedIndex;

  MyHomePage({required this.selectedIndex, Key? key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;

  static List<Widget> _pages = <Widget>[
    landingPage(),
    pendingDiagnosesPage(),
    pastDiagnosesPage(),
    MyProfilePage(),
  ];

  static List<String> _titles = <String>[
    'MediSwift',
    'Pending Diagnoses',
    'Past Diagnoses',
    'My Profile',
  ];

  @override
  void initState() {
    super.initState();
    _selectedIndex = widget.selectedIndex;
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_titles[_selectedIndex]),
        leading: IconButton(
            onPressed: () {
              Get.toNamed('/');
            },
            icon: Icon(Icons.circle)),
      ),
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.pending),
            label: 'Pending Diagnoses',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.history),
            label: 'Past Diagnoses',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'My Profile',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.blue,
        onTap: _onItemTapped,
      ),
    );
  }
}
