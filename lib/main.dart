import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:get/get.dart';
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
        GetPage(name: '/', title: 'Home', page: () => landingPage()),
        GetPage(name: '/logInPage', title: 'Log In', page: () => LogInPage()),
        GetPage(
            name: '/signUpPage', title: 'Sign Up', page: () => SignUpPage()),
        //GetPage(name: '/eachDiagnosis', title: 'Home', page: () => landingPage()),
        GetPage(
            name: '/pastDiagnosesPage',
            title: 'Past Diagnoses',
            page: () => pastDiagnosesPage()),
        GetPage(
            name: '/pendingDiagnosesPage',
            title: 'Pending Diagnoses',
            page: () => pendingDiagnosesPage()),
      ],
    );
  }
}
