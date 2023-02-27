import 'package:flutter/material.dart';

class homePage extends StatefulWidget {
  const homePage({super.key});

  @override
  State<homePage> createState() => _homePageState();
}

class _homePageState extends State<homePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //appBar: AppBar(),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                //crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '09',
                    style: TextStyle(fontSize: 48),
                  ),
                  Text('Patients diagnosed')
                ],
              ),
              SizedBox(
                width: 40,
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    '04',
                    style: TextStyle(fontSize: 48),
                  ),
                  Text('Patients pending')
                ],
              ),
            ],
          )
        ],
      ),
    );
  }
}
