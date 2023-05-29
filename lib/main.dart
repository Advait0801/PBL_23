import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
import 'package:pbl/home_page.dart';
import 'package:pbl/screens/LandingPage.dart';
import 'package:pbl/screens/log_in_page.dart';
import 'package:pbl/screens/past_diagnosis.dart';
import 'package:pbl/screens/pending_diagnosis.dart';
import 'package:pbl/screens/sign_up_page.dart';
import 'firebase_options.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  print("firebas einitialized");
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData.dark(),
      initialRoute: '/',
      getPages: [
        // GetPage(name: '/landingPage', title: 'Landing Page', page: () => landingPage()),
        // GetPage(name: '/logInPage', title: 'Log In', page: () => LogInPage()),
        // GetPage(
        //     name: '/signUpPage', title: 'Sign Up', page: () => SignUpPage()),
        // GetPage(
        //     name: '/pastDiagnosesPage',
        //     title: 'Past Diagnoses',
        //     page: () => pastDiagnosesPage()),
        // GetPage(
        //     name: '/pendingDiagnosesPage',
        //     title: 'Pending Diagnoses',
        //     page: () => pendingDiagnosesPage()),
        GetPage(
            name: '/',
            title: 'Home',
            page: () => MyHomePage(
                  selectedIndex: 0,
                )),
        GetPage(
            name: '/pendingDiagnosesPage',
            title: 'Pending Diagnoses',
            page: () => MyHomePage(
                  selectedIndex: 1,
                )),
        GetPage(
            name: '/pastDiagnosisPage',
            title: 'Past Diagnoses',
            page: () => MyHomePage(
                  selectedIndex: 2,
                )),
        GetPage(
            name: '/myProfilePage',
            title: 'My Profile',
            page: () => MyHomePage(
                  selectedIndex: 3,
                )),
      ],
    );
  }
}
